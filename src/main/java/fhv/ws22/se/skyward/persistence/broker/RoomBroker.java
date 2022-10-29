package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.RoomDto;
import fhv.ws22.se.skyward.model.DTOs.RoomStateDto;
import fhv.ws22.se.skyward.model.Room;
import fhv.ws22.se.skyward.model.RoomState;

import javax.persistence.EntityManager;
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
            roomDtos.add(r.toDto());
        }

        return roomDtos;
    }

    public void add(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.persist(room.toRoom());
        entityManager.getTransaction().commit();
    }

    public void update(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.merge(room.toRoom());
        entityManager.getTransaction().commit();
    }

    public void delete(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.remove(room.toRoom());
        entityManager.getTransaction().commit();
    }
}
