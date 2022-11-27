package fhv.ws22.se.skyward.persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fhv.ws22.se.skyward.domain.DataService;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.broker.*;

import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.ChargeableItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.*;

@Singleton
public class DatabaseFacade implements DataService {
    @Inject
    EntityManager entityManager;

    private final CustomerBroker customerBroker;
    private final RoomBroker roomBroker;
    private final BookingBroker bookingBroker;
    private final AddressBroker addressBroker;
    private final InvoiceBroker invoiceBroker;
    private final ChargeableItemBroker chargeableItemBroker;

    private Map<Class<? extends AbstractModel>,
            BrokerBase<? extends AbstractModel>> brokersClassMap;

    public DatabaseFacade() {
        brokersClassMap = new HashMap<>();

        BrokerInstanceFactory brokerInstanceFactory = BrokerInstanceFactory.getInstance();
        customerBroker = brokerInstanceFactory.getCustomerBroker();
        roomBroker = brokerInstanceFactory.getRoomBroker();
        bookingBroker = brokerInstanceFactory.getBookingBroker();
        addressBroker = brokerInstanceFactory.getAddressBroker();
        invoiceBroker = brokerInstanceFactory.getInvoiceBroker();
        chargeableItemBroker = brokerInstanceFactory.getChargeableItemBroker();

        brokersClassMap.put(CustomerModel.class, customerBroker);
        brokersClassMap.put(RoomModel.class, roomBroker);
        brokersClassMap.put(BookingModel.class, bookingBroker);
        brokersClassMap.put(InvoiceModel.class, invoiceBroker);
        brokersClassMap.put(AddressModel.class, addressBroker);
        brokersClassMap.put(ChargeableItemModel.class, chargeableItemBroker);
    }

    public <T extends AbstractModel> List<? extends AbstractModel> getAll(Class<T> clazz) {
        return brokersClassMap.get(clazz).getAll();
    }
    public <T extends AbstractModel> T get(UUID id, Class<T> clazz) {
        return brokersClassMap.get(clazz).get(id, AbstractModel.getEntityClass(clazz));
    }
    public <T extends AbstractModel> void add(T t) {
        brokersClassMap.get(t.getClass()).add(t);
    }
    public <T extends AbstractModel> UUID addAndReturnId(Class<T> clazz, T t) {
        return brokersClassMap.get(clazz).addAndReturnId(t);
    }
    public <T extends AbstractModel> void update(UUID id, T t) {
        brokersClassMap.get(t.getClass()).update(id, t);
    }
    public <T extends AbstractModel> void delete(UUID id, Class<T> clazz) {
        brokersClassMap.get(clazz).delete(id, AbstractModel.getEntityClass(clazz));
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
