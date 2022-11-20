package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.entity.*;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
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
        List<ChargeableItemModel> newChargeableItems = new ArrayList<ChargeableItemModel>();
        if (booking.getRooms() != null) {
            for (RoomModel room : booking.getRooms()) {
                String name = room.getRoomTypeName() + "room";
                newChargeableItems.add(new ChargeableItemModel(name, new BigDecimal("100"), 1));
            }
        }
        booking.setChargeableItems(newChargeableItems);


        List<CustomerModel> customerModels = booking.getCustomers();
        List<Customer> customers = new ArrayList<Customer>();
        if (customerModels != null) {
            for (CustomerModel customerModel : customerModels) {
                customers.add(customerModel.toEntity());
            }
        }

        List<RoomModel> roomModels = booking.getRooms();
        List<Room> rooms = new ArrayList<Room>();
        if (roomModels != null) {
            for (RoomModel roomModel : roomModels) {
                rooms.add(roomModel.toEntity());
            }
        }

        List<ChargeableItemModel> chargeableItemModels = booking.getChargeableItems();
        List<ChargeableItem> chargeableItems = new ArrayList<ChargeableItem>();
        if (chargeableItemModels != null) {
            for (ChargeableItemModel chargeableItemModel : chargeableItemModels) {
                ChargeableItem c = chargeableItemModel.toEntity();
                chargeableItems.add(c);
                if (entityManager.createQuery("FROM ChargeableItem c WHERE c.name = :name AND c.price = :price AND c.quantity = :quantity")
                        .setParameter("name", c.getName())
                        .setParameter("price", c.getPrice())
                        .setParameter("quantity", c.getQuantity())
                        .getResultList().isEmpty()) {
                    entityManager.getTransaction().begin();
                    entityManager.persist(c);
                    entityManager.getTransaction().commit();
                }
            }
        }

        List<InvoiceModel> invoiceModels = booking.getInvoices();
        List<Invoice> invoices = new ArrayList<Invoice>();
        if (invoiceModels != null) {
            for (InvoiceModel invoiceModel : invoiceModels) {
                invoices.add(invoiceModel.toEntity());
            }
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
        List<Invoice> invoicesInDb = new ArrayList<Invoice>();
        for (Invoice i : invoices) {
            invoicesInDb.add((Invoice) entityManager.createQuery("FROM Invoice WHERE invoiceNumber = :number")
                .setParameter("number", i.getInvoiceNumber())
                .getSingleResult());
        }
        List<ChargeableItem> chargeableItemsInDb = new ArrayList<ChargeableItem>();
        for (ChargeableItem ci : chargeableItems) {
            chargeableItemsInDb.add((ChargeableItem) entityManager.createQuery("FROM ChargeableItem WHERE name = :name")
                .setParameter("name", ci.getName())
                .getSingleResult());
        }

        Booking bookingEntity = booking.toEntity();
        bookingEntity.setCustomers(customersInDb);
        bookingEntity.setRooms(roomsInDb);
        bookingEntity.setInvoices(invoicesInDb);
        bookingEntity.setChargeableItems(chargeableItemsInDb);

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
