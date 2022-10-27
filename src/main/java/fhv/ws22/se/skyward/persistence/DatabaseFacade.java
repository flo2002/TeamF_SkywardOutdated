package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.AbstractEntity;
import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.broker.BrokerBase;
import fhv.ws22.se.skyward.persistence.broker.PersonBroker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        brokers.put(Person.class, new PersonBroker(entityManager));
    }

    public List<Person> getAllPersons() {
        PersonBroker broker = new PersonBroker(entityManager);
        return broker.getAll();
    }
    public <T extends AbstractEntity> void add(T t) {
        brokers.get(t.getClass()).add(t);
    }

    public <T extends AbstractEntity> void update(T t) {
        brokers.get(t.getClass()).update(t);
    };
    public <T extends AbstractEntity> void delete(T t) {
        brokers.get(t.getClass()).delete(t);
    };
}
