package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.Person;

import javax.persistence.EntityManager;
import java.util.List;

public class PersonBroker extends BrokerBase<Person> {
    private final EntityManager entityManager;

    public PersonBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<Person> getAll() {
        List<Person> persons = (List<Person>) entityManager.createQuery("FROM Person").getResultList();
        return persons;
    }

    public void add(Person person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    public void update(Person person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

    public void delete(Person person) {
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }
}
