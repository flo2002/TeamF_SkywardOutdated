package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.Customer;
import fhv.ws22.se.skyward.persistence.entity.Room;
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
        List<Booking> rooms = entityManager.createQuery("FROM Booking").getResultList();

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
        List<CustomerModel> customerModels = booking.getCustomers();
        List<Customer> customers = new ArrayList<Customer>();
        for (CustomerModel customerModel : customerModels) {
            customers.add(customerModel.toEntity());
        }

        List<RoomModel> roomModels = booking.getRooms();
        List<Room> rooms = new ArrayList<Room>();
        for (RoomModel roomModel : roomModels) {
            rooms.add(roomModel.toEntity());
        }

        entityManager.getTransaction().begin();
        List<Customer> customersInDb = new ArrayList<Customer>();
        for (Customer c : customers) {
            customersInDb.add((Customer) entityManager.createQuery("FROM Customer WHERE firstName = :firstname AND lastName = :lastname")
                .setParameter("firstname", c.getFirstName())
                .setParameter("lastname", c.getLastName())
                .getSingleResult());
        }
        List<Room> roomsInDb = new ArrayList<Room>();
        for (Room r : rooms) {
            roomsInDb.add((Room) entityManager.createQuery("FROM Room WHERE roomNumber = :number")
                .setParameter("number", r.getRoomNumber())
                .getSingleResult());
        }

        Booking bookingEntity = booking.toEntity();
        bookingEntity.setCustomers(customersInDb);
        bookingEntity.setRooms(roomsInDb);

        entityManager.persist(bookingEntity);
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
