package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.PersonDTO;
import fhv.ws22.se.skyward.model.Person;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PersonBroker extends BrokerBase<PersonDTO> {
    private final EntityManager entityManager;

    public PersonBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<PersonDTO> getAll() {
        List<Person> persons = (List<Person>) entityManager.createQuery("FROM Person").getResultList();

        List<PersonDTO> personDTOs = new ArrayList<PersonDTO>();
        for (Person p : persons) {
            personDTOs.add(p.toDTO());
        }

        return personDTOs;
    }

    public void add(PersonDTO person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person.toPerson());
        entityManager.getTransaction().commit();
    }

    public void update(PersonDTO person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

    public void delete(PersonDTO person) {
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }
}
