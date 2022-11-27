package fhv.ws22.se.skyward.persistence.broker;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.persistence.entity.*;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingBroker extends BrokerBase<BookingModel> {
    @Inject
    DatabaseFacade dbf;
    CustomerBroker customerBroker;
    RoomBroker roomBroker;
    InvoiceBroker invoiceBroker;
    ChargeableItemBroker chargeableItemBroker;

    public BookingBroker(EntityManager entityManager) {
        super(entityManager);
        customerBroker = dbf.getCustomerBroker();
        roomBroker = dbf.getRoomBroker();
        invoiceBroker = dbf.getInvoiceBroker();
        chargeableItemBroker = dbf.getChargeableItemBroker();
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

    public <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        return super.get(id, entityClazz);
    }

    private void addDependenciesIfNotExists(BookingModel booking) {
        List<CustomerModel> customerModels = booking.getCustomers();
        customerModels.forEach(customerBroker::add);

        List<RoomModel> roomModels = booking.getRooms();
        roomModels.forEach(roomBroker::add);

        List<ChargeableItemModel> chargeableItemModels = booking.getChargeableItems();
        chargeableItemModels.forEach(chargeableItemBroker::add);

        List<InvoiceModel> invoiceModels = booking.getInvoices();
        invoiceModels.forEach(invoiceBroker::add);
    }

    public<S extends AbstractModel> UUID addAndReturnId(S s) {
        BookingModel booking = (BookingModel) s;
        addDependenciesIfNotExists(booking);

        List<Customer> customers = new ArrayList<Customer>();
        booking.getCustomers().forEach(customer -> customers.add(
            entityManager.createQuery("FROM Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName", Customer.class)
                .setParameter("firstName", customer.getFirstName())
                .setParameter("lastName", customer.getLastName())
                .getSingleResult()
        ));


        List<Room> rooms = new ArrayList<Room>();
        booking.getRooms().forEach(room -> rooms.add(
            entityManager.createQuery("FROM Room WHERE roomNumber = :number", Room.class)
                .setParameter("number", room.getRoomNumber())
                .getSingleResult()
        ));

        List<Invoice> invoices = new ArrayList<Invoice>();
        booking.getInvoices().forEach(invoice -> invoices.add(
            entityManager.createQuery("FROM Invoice WHERE invoiceNumber = :number", Invoice.class)
                .setParameter("number", invoice.getInvoiceNumber())
                .getSingleResult()
        ));

        List<ChargeableItem> chargeableItems = new ArrayList<ChargeableItem>();
        booking.getChargeableItems().forEach(chargeableItem -> chargeableItems.add(
                entityManager.createQuery("FROM ChargeableItem WHERE name = :name AND price = :price AND quantity = :quantity", ChargeableItem.class)
                        .setParameter("name", chargeableItem.getName())
                        .setParameter("price", chargeableItem.getPrice())
                        .setParameter("quantity", chargeableItem.getQuantity())
                        .getSingleResult()
        ));

        Booking bookingEntity = booking.toEntity();
        bookingEntity.setCustomers(customers);
        bookingEntity.setRooms(rooms);
        bookingEntity.setInvoices(invoices);
        bookingEntity.setChargeableItems(chargeableItems);

        entityManager.getTransaction().begin();
        entityManager.persist(bookingEntity);
        entityManager.getTransaction().commit();
        return booking.getId();
    }

    public <S extends AbstractModel> void add(S s) {
        addAndReturnId(s);
    }

    public <S extends AbstractModel> void update(UUID id, S s) {
        super.update(id, s);
    }

    public void delete(UUID id, Class<? extends AbstractEntity> clazz) {
        super.delete(id, clazz);
    }
}
