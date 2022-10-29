package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.PersonDto;
import fhv.ws22.se.skyward.model.Person;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PersonBroker extends BrokerBase<PersonDto> {
    private final EntityManager entityManager;

    public PersonBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<PersonDto> getAll() {
        List<Person> persons = (List<Person>) entityManager.createQuery("FROM Person").getResultList();

        List<PersonDto> personDtos = new ArrayList<PersonDto>();
        for (Person p : persons) {
            personDtos.add(p.toDTO());
        }

        return personDtos;
    }

    public void add(PersonDto person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person.toPerson());
        entityManager.getTransaction().commit();
    }

    public void update(PersonDto person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person);
        entityManager.getTransaction().commit();
    }

    public void delete(PersonDto person) {
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }
}
