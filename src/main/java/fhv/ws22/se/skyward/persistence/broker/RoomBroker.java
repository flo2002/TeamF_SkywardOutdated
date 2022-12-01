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

        if (entityManager.createQuery("FROM Room WHERE roomNumber = :number")
                .setParameter("number", room.getRoomNumber())
                .getResultList().isEmpty()) {
            entityManager.getTransaction().begin();
            entityManager.persist(roomEntity);
            entityManager.getTransaction().commit();
        }

        return roomEntity.getId();
    }
}
