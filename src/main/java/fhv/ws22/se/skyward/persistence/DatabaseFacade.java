package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.DTOs.*;
import fhv.ws22.se.skyward.persistence.broker.*;

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
        brokers.put(PersonDto.class, new PersonBroker(entityManager));
        brokers.put(RoomDto.class, new RoomBroker(entityManager));
    }

    public List<PersonDto> getAllPersons() {
        PersonBroker broker = new PersonBroker(entityManager);
        return broker.getAll();
    }
    public <T extends AbstractDto> void add(T t) {
        brokers.get(t.getClass()).add(t);
    }

    public <T extends AbstractDto> void update(T t) {
        brokers.get(t.getClass()).update(t);
    };
    public <T extends AbstractDto> void delete(T t) {
        brokers.get(t.getClass()).delete(t);
    };
}
