package fhv.ws22.se.skyward.persistence;

import com.google.inject.Singleton;
import fhv.ws22.se.skyward.domain.DataService;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.broker.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.*;

@Singleton
public class DatabaseFacade implements DataService {
    private Map<Class, BrokerBase> brokers;

    public DatabaseFacade() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("skyward");
        EntityManager entityManager = fact.createEntityManager();

        brokers = new HashMap<Class, BrokerBase>();
        brokers.put(CustomerModel.class, new CustomerBroker(entityManager));
        brokers.put(RoomModel.class, new RoomBroker());
        brokers.put(BookingModel.class, new BookingBroker(entityManager));
        brokers.put(InvoiceModel.class, new InvoiceBroker(entityManager));
        brokers.put(AddressModel.class, new AddressBroker(entityManager));
        brokers.put(ChargeableItemModel.class, new ChargeableItemBroker(entityManager));
    }

    public <T extends AbstractModel> List getAll(Class<T> clazz) {
        return brokers.get(clazz).getAll();
    }
    public <T extends AbstractModel> T get(UUID id, Class<T> clazz) {
        return (T) brokers.get(clazz).get(id);
    };
    public <T extends AbstractModel> void add(T t) {
        brokers.get(t.getClass()).add(t);
    }
    public <T extends AbstractModel> UUID addAndReturnId(Class<T> clazz, T t) {
        return brokers.get(clazz).addAndReturnId(t);
    }
    public <T extends AbstractModel> void update(UUID id, T t) {
        brokers.get(t.getClass()).update(id, t);
    };
    public <T extends AbstractModel> void delete(UUID id, Class<T> clazz) {
        brokers.get(clazz).delete(id, clazz);
    };
}
