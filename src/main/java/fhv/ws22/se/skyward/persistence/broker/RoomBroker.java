package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Room;

import fhv.ws22.se.skyward.persistence.entity.RoomState;
import fhv.ws22.se.skyward.persistence.entity.RoomType;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomBroker extends BrokerBase<RoomModel> {
    public RoomBroker(EntityManager entityManager) {
        super(entityManager);
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

    public <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        return super.get(id, entityClazz);
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
    }


    public <S extends AbstractModel> UUID addAndReturnId(S s) {
        RoomModel room = (RoomModel) s;
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

        if (entityManager.createQuery("FROM Room WHERE number = :number")
                .setParameter("number", room.getRoomNumber())
                .getSingleResult() != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(roomEntity);
            entityManager.getTransaction().commit();
        }

        return roomEntity.getId();
    }

    public <S extends AbstractModel> void add(S s) {
        addAndReturnId(s);
    }

    public <S extends AbstractModel> void update(UUID id, S s) {
        super.update(id, s);
    }

    public void delete(UUID id, Class<? extends AbstractEntity> clazz) {
        super.delete(id, clazz);
    }
}
