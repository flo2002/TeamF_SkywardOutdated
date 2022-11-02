package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.DTOs.*;
import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.broker.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
        brokers.put(BookingDto.class, new BookingBroker(entityManager));
    }

    public PersonDto getPersonByNames(String firstName, String lastName) {
        PersonBroker broker = (PersonBroker) brokers.get(PersonDto.class);
        return broker.getPersonByNames(firstName, lastName);
    }
    public <T extends AbstractDto> List getAll(T t) {
        return brokers.get(t.getClass()).getAll();
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
