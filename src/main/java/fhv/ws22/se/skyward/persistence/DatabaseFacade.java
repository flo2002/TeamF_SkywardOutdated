package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.broker.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DatabaseFacade {
    private final EntityManager entityManager;
    private Map<Class, BrokerBase> brokers;

    private static DatabaseFacade singleton;

    public static synchronized DatabaseFacade getInstance() {
        if (singleton == null) {
            singleton = new DatabaseFacade();
        }
        return singleton;
    }

    public DatabaseFacade() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("skyward");
        this.entityManager = fact.createEntityManager();

        brokers = new HashMap<Class, BrokerBase>();
        brokers.put(CustomerModel.class, new CustomerBroker(entityManager));
        brokers.put(RoomModel.class, new RoomBroker(entityManager));
        brokers.put(BookingModel.class, new BookingBroker(entityManager));
    }

    public CustomerModel getCustomerByNames(String firstName, String lastName) {
        CustomerBroker broker = (CustomerBroker) brokers.get(CustomerModel.class);
        return broker.getCustomerByNames(firstName, lastName);
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
        brokers.get(clazz).delete(id);
    };

}
