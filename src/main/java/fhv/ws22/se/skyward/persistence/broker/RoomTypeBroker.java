package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.RoomTypeDto;
import fhv.ws22.se.skyward.model.RoomType;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeBroker extends BrokerBase<RoomTypeDto> {
    private final EntityManager entityManager;

    public RoomTypeBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<RoomTypeDto> getAll() {
        List<RoomType> roomTypes = (List<RoomType>) entityManager.createQuery("FROM RoomType").getResultList();

        List<RoomTypeDto> roomTypeDtos = new ArrayList<RoomTypeDto>();
        for (RoomType rt : roomTypes) {
            roomTypeDtos.add(rt.toDto());
        }

        return roomTypeDtos;
    }

    public void add(RoomTypeDto roomType) {
        entityManager.getTransaction().begin();
        entityManager.persist(roomType.toRoomType());
        entityManager.getTransaction().commit();
    }

    public void update(RoomTypeDto roomType) {
        entityManager.getTransaction().begin();
        entityManager.merge(roomType.toRoomType());
        entityManager.getTransaction().commit();
    }

    public void delete(RoomTypeDto roomType) {
        entityManager.getTransaction().begin();
        entityManager.remove(roomType.toRoomType());
        entityManager.getTransaction().commit();
    }
}