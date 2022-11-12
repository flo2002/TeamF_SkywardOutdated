package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.entity.Room;

import fhv.ws22.se.skyward.persistence.entity.RoomState;
import fhv.ws22.se.skyward.persistence.entity.RoomType;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomBroker extends BrokerBase<RoomModel> {
    private final EntityManager entityManager;

    public RoomBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
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

    public void add(RoomModel room) {
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

        if (roomState == null) {
            roomState = (RoomState) entityManager.createQuery("FROM RoomState WHERE name = :name")
                .setParameter("name", room.getRoomStateName())
                .getSingleResult();
        }
        if (roomType == null) {
            roomType = (RoomType) entityManager.createQuery("FROM RoomType WHERE name = :name")
                .setParameter("name", room.getRoomTypeName())
                .getSingleResult();
        }

        Room roomEntity = room.toEntity();
        roomEntity.setRoomState(roomState);
        roomEntity.setRoomType(roomType);

        entityManager.persist(roomEntity);
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, RoomModel room) {
        Room tmpRoom = room.toEntity();
        tmpRoom.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpRoom);
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Room.class, id));
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(RoomModel room) {
        Room tmpRoom = room.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(tmpRoom);
        entityManager.getTransaction().commit();
        return tmpRoom.getId();
    }
}
