package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Customer;
import fhv.ws22.se.skyward.persistence.entity.Room;

import fhv.ws22.se.skyward.persistence.entity.RoomState;
import fhv.ws22.se.skyward.persistence.entity.RoomType;
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
        RoomState roomState = null;
        entityManager.getTransaction().begin();
        if (entityManager.createQuery("FROM RoomState WHERE name = :name")
                .setParameter("name", room.getRoomStateName())
                .getResultList().isEmpty()) {
            roomState = new RoomState();
            roomState.setName(room.getRoomStateName());

            entityManager.persist(roomState);
            entityManager.flush();
            System.out.println("RoomState did not exist and therefore was created");
        }

        if (roomState == null) {
            roomState = (RoomState) entityManager.createQuery("FROM RoomState WHERE name = :name")
                    .setParameter("name", room.getRoomStateName())
                    .getSingleResult();
        }

        Room roomEntity = room.toEntity();
        roomEntity.setRoomState(roomState);

        entityManager.persist(roomEntity);
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
