package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.RoomDto;
import fhv.ws22.se.skyward.model.Room;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RoomBroker extends BrokerBase<RoomDto> {
    private EntityManager entityManager;

    public RoomBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<RoomDto> getAll() {
        List<Room> rooms = (List<Room>) entityManager.createQuery("FROM Room").getResultList();

        List<RoomDto> roomDtos = new ArrayList<RoomDto>();
        for (Room r : rooms) {
            roomDtos.add(RoomDto.toDto(r));
        }

        return roomDtos;
    }

    public void add(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.persist(room.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.merge(room.toEntity());
        entityManager.getTransaction().commit();
    }

    public void delete(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.remove(room.toEntity());
        entityManager.getTransaction().commit();
    }
}
