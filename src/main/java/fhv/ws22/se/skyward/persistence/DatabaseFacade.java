package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.AbstractEntity;
import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.broker.BrokerBase;
import fhv.ws22.se.skyward.persistence.broker.PersonBroker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DatabaseFacade {
    private EntityManager entityManager;

    public DatabaseFacade() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("skyward");
        this.entityManager = fact.createEntityManager();
    }

    public List<Person> getAllPersons() {
        PersonBroker broker = new PersonBroker(entityManager);
        return broker.getAll();
    }
    public <T extends AbstractEntity> void add(T t) {
        if (t instanceof Person) {
            PersonBroker broker = new PersonBroker(entityManager);
            broker.add((Person) t);
        }
    };
    public <T extends AbstractEntity> void update(T t) {
        if (t instanceof Person) {
            PersonBroker broker = new PersonBroker(entityManager);
            broker.update((Person) t);
        }
    };
    public <T extends AbstractEntity> void delete(T t) {
        if (t instanceof Person) {
            PersonBroker broker = new PersonBroker(entityManager);
            broker.delete((Person) t);
        }
    };
}
