package fhv.ws22.se.skyward.persistence.broker;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.persistence.entity.*;
import jakarta.persistence.EntityManager;
import javafx.scene.chart.PieChart;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingBroker extends BrokerBase<BookingModel> {
    private final DatabaseFacade dbf;

    public BookingBroker(DatabaseFacade databaseFacade, EntityManager entityManager) {
        super(entityManager);
        dbf = databaseFacade;
    }

    private void addDependenciesIfNotExists(BookingModel booking) {
        List<CustomerModel> customerModels = booking.getCustomers();
        if (customerModels != null) {
            customerModels.forEach(dbf::add);
        }

        List<RoomModel> roomModels = booking.getRooms();
        if (roomModels != null) {
            roomModels.forEach(dbf::add);
        }
    }

    public<S extends AbstractModel> UUID addAndReturnId(S s) {
        BookingModel booking = (BookingModel) s;
        addDependenciesIfNotExists(booking);

        List<Customer> customers = new ArrayList<Customer>();
        if (booking.getCustomers() != null) {
            booking.getCustomers().forEach(customer -> customers.add(
                entityManager.createQuery("FROM Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName", Customer.class)
                    .setParameter("firstName", customer.getFirstName())
                    .setParameter("lastName", customer.getLastName())
                    .getSingleResult()
            ));
        }


        List<Room> rooms = new ArrayList<Room>();
        if (booking.getRooms() != null) {
            booking.getRooms().forEach(room -> rooms.add(
                entityManager.createQuery("FROM Room WHERE roomNumber = :number", Room.class)
                    .setParameter("number", room.getRoomNumber())
                    .getSingleResult()
            ));
        }


        Booking bookingEntity = booking.toEntity();
        bookingEntity.setCustomers(customers);
        bookingEntity.setRooms(rooms);

        entityManager.getTransaction().begin();
        entityManager.persist(bookingEntity);
        entityManager.getTransaction().commit();
        return bookingEntity.getId();
    }
}
