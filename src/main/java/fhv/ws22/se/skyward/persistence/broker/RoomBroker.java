package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Customer;
import fhv.ws22.se.skyward.persistence.entity.Room;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomBroker extends BrokerBase<RoomDto> {
    private final EntityManager entityManager;

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

    public RoomDto get(UUID id) {
        Room room = entityManager.find(Room.class, id);
        return RoomDto.toDto(room);
    }

    public void add(RoomDto room) {
        entityManager.getTransaction().begin();
        entityManager.persist(room.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, RoomDto room) {
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

    public UUID addAndReturnId(RoomDto room) {
        Room tmpRoom = room.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(tmpRoom);
        entityManager.getTransaction().commit();
        return tmpRoom.getId();
    }
}
