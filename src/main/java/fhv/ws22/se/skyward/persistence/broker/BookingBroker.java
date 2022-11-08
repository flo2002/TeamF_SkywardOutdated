package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.Booking;
import fhv.ws22.se.skyward.model.DTOs.BookingDto;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingBroker extends BrokerBase<BookingDto> {
    private final EntityManager entityManager;

    public BookingBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<BookingDto> getAll() {
        List<Booking> rooms = (List<Booking>) entityManager.createQuery("FROM Booking").getResultList();

        List<BookingDto> roomDtos = new ArrayList<BookingDto>();
        for (Booking r : rooms) {
            roomDtos.add(BookingDto.toDto(r));
        }

        return roomDtos;
    }

    public void add(BookingDto booking) {
        entityManager.getTransaction().begin();
        entityManager.persist(booking.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, BookingDto booking) {
        entityManager.getTransaction().begin();
        entityManager.merge(booking.toEntity());
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Booking.class, id));
        entityManager.getTransaction().commit();
    }
}
