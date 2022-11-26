package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.entity.Room;

import fhv.ws22.se.skyward.persistence.entity.RoomState;
import fhv.ws22.se.skyward.persistence.entity.RoomType;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomBroker extends BrokerBase<RoomModel> implements PersistenceRepository<RoomModel> {

    public RoomBroker() {
    }

    @SuppressWarnings("unchecked")
    public List<RoomModel> getAll() {
        List<Room> rooms = entityManager.createQuery("FROM Room").getResultList();

        List<RoomModel> roomModels = new ArrayList<RoomModel>();
        for (Room r : rooms) {
            roomModels.add(RoomModel.toModel(r));
        }

        return roomModels;
    }

    public RoomModel get(UUID id) {
        Room room = entityManager.find(Room.class, id);
        return RoomModel.toModel(room);
    }

    private void addDependenciesIfNotExists(RoomModel room) {
        RoomState roomState = null;
        RoomType roomType = null;

        entityManager.getTransaction().begin();
        if (entityManager.createQuery("FROM RoomState WHERE name = :name")
                .setParameter("name", room.getRoomStateName())
                .getResultList().isEmpty()) {
            roomState = new RoomState();
            roomState.setName(room.getRoomStateName());
            entityManager.persist(roomState);
            entityManager.flush();
        }
        if (entityManager.createQuery("FROM RoomType WHERE name = :name")
                .setParameter("name", room.getRoomTypeName())
                .getResultList().isEmpty()) {
            roomType = new RoomType();
            roomType.setName(room.getRoomTypeName());
            entityManager.persist(roomType);
            entityManager.flush();
        }
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(RoomModel room) {
        addDependenciesIfNotExists(room);

        RoomState roomState = (RoomState) entityManager.createQuery("FROM RoomState WHERE name = :name")
                .setParameter("name", room.getRoomStateName())
                .getSingleResult();
        RoomType roomType = (RoomType) entityManager.createQuery("FROM RoomType WHERE name = :name")
                .setParameter("name", room.getRoomTypeName())
                .getSingleResult();

        Room roomEntity = room.toEntity();
        roomEntity.setRoomState(roomState);
        roomEntity.setRoomType(roomType);

        entityManager.getTransaction().begin();
        entityManager.persist(roomEntity);
        entityManager.getTransaction().commit();
        return roomEntity.getId();
    }

    public void add(RoomModel room) {
        addAndReturnId(room);
    }

    public void update(UUID id, RoomModel room) {
        Room tmpRoom = room.toEntity();
        tmpRoom.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpRoom);
        entityManager.getTransaction().commit();
    }
}
