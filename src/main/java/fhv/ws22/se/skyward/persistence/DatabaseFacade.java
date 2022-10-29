package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.DTOs.DTOMarker;
import fhv.ws22.se.skyward.model.DTOs.PersonDto;
import fhv.ws22.se.skyward.model.DTOs.RoomStateDto;
import fhv.ws22.se.skyward.model.DTOs.RoomTypeDto;
import fhv.ws22.se.skyward.model.Room;
import fhv.ws22.se.skyward.model.RoomState;
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
        brokers.put(RoomTypeDto.class, new RoomTypeBroker(entityManager));
        brokers.put(RoomStateDto.class, new RoomStateBroker(entityManager));
        brokers.put(Room.class, new RoomBroker(entityManager));
    }

    public List<PersonDto> getAllPersons() {
        PersonBroker broker = new PersonBroker(entityManager);
        return broker.getAll();
    }
    public <T extends DTOMarker> void add(T t) {
        brokers.get(t.getClass()).add(t);
    }

    public <T extends DTOMarker> void update(T t) {
        brokers.get(t.getClass()).update(t);
    };
    public <T extends DTOMarker> void delete(T t) {
        brokers.get(t.getClass()).delete(t);
    };
}
