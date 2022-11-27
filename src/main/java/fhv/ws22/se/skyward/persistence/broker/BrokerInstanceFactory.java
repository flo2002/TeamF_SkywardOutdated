package fhv.ws22.se.skyward.persistence.broker;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import jakarta.persistence.EntityManager;

public class BrokerInstanceFactory {
    @Inject
    static EntityManager entityManager;
    @Inject
    static DatabaseFacade dbf;

    private static BrokerInstanceFactory instance;

    private static CustomerBroker customerBroker;
    private static RoomBroker roomBroker;
    private static BookingBroker bookingBroker;
    private static AddressBroker addressBroker;
    private static InvoiceBroker invoiceBroker;
    private static ChargeableItemBroker chargeableItemBroker;

    private BrokerInstanceFactory() {
    }

    public static BrokerInstanceFactory getInstance() {
        if (instance == null) {
            instance = new BrokerInstanceFactory();
            customerBroker = new CustomerBroker(dbf, entityManager);
            roomBroker = new RoomBroker(entityManager);
            bookingBroker = new BookingBroker(entityManager);
            addressBroker = new AddressBroker(entityManager);
            invoiceBroker = new InvoiceBroker(entityManager);
            chargeableItemBroker = new ChargeableItemBroker(entityManager);
        }
        return instance;
    }

    public BrokerInstanceFactory getBrokerInstance() {
        return instance;
    }

    public CustomerBroker getCustomerBroker() {
        return customerBroker;
    }
    public RoomBroker getRoomBroker() {
        return roomBroker;
    }
    public BookingBroker getBookingBroker() {
        return bookingBroker;
    }
    public InvoiceBroker getInvoiceBroker() {
        return invoiceBroker;
    }
    public AddressBroker getAddressBroker() {
        return addressBroker;
    }
    public ChargeableItemBroker getChargeableItemBroker() {
        return chargeableItemBroker;
    }
}
