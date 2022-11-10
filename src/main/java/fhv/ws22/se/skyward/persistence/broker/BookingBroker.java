package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingBroker extends BrokerBase<BookingModel> {
    private final EntityManager entityManager;

    public BookingBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<BookingModel> getAll() {
        List<Booking> rooms = (List<Booking>) entityManager.createQuery("FROM Booking").getResultList();

        List<BookingModel> roomModels = new ArrayList<BookingModel>();
        for (Booking r : rooms) {
            roomModels.add(BookingModel.toModel(r));
        }

        return roomModels;
    }

    public BookingModel get(UUID id) {
        Booking booking = entityManager.find(Booking.class, id);
        return BookingModel.toModel(booking);
    }

    public void add(BookingModel booking) {
        entityManager.getTransaction().begin();
        entityManager.persist(booking.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, BookingModel booking) {
        entityManager.getTransaction().begin();
        entityManager.merge(booking.toEntity());
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Booking.class, id));
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(BookingModel booking) {
        Booking tmpBooking = booking.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(tmpBooking);
        entityManager.getTransaction().commit();
        return tmpBooking.getId();
    }
}
