package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.RoomStateDto;
import fhv.ws22.se.skyward.model.DTOs.RoomTypeDto;
import fhv.ws22.se.skyward.model.RoomState;
import fhv.ws22.se.skyward.model.RoomType;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class RoomStateBroker extends BrokerBase<RoomStateDto> {
    private EntityManager entityManager;

    public RoomStateBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<RoomStateDto> getAll() {
        List<RoomState> roomStates = (List<RoomState>) entityManager.createQuery("FROM RoomState").getResultList();

        List<RoomStateDto> roomStateDtos = new ArrayList<RoomStateDto>();
        for (RoomState rs : roomStates) {
            roomStateDtos.add(RoomStateDto.toDto(rs));
        }

        return roomStateDtos;
    }

    public void add(RoomStateDto roomState) {
        entityManager.getTransaction().begin();
        entityManager.persist(roomState.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(RoomStateDto roomState) {
        entityManager.getTransaction().begin();
        entityManager.merge(roomState.toEntity());
        entityManager.getTransaction().commit();
    }

    public void delete(RoomStateDto roomState) {
        entityManager.getTransaction().begin();
        entityManager.remove(roomState.toEntity());
        entityManager.getTransaction().commit();
    }
}


