package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.DTOs.PersonDto;
import fhv.ws22.se.skyward.model.Person;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PersonBroker extends BrokerBase<PersonDto> {
    private final EntityManager entityManager;

    public PersonBroker(EntityManager em) {
        entityManager = em;
    }

    @SuppressWarnings("unchecked")
    public List<PersonDto> getAll() {
        List<Person> persons = (List<Person>) entityManager.createQuery("FROM Person").getResultList();

        List<PersonDto> personDtos = new ArrayList<PersonDto>();
        for (Person p : persons) {
            personDtos.add(PersonDto.toDto(p));
        }

        return personDtos;
    }

    public PersonDto getPersonByNames(String firstName, String lastName) {
        Person p = (Person) entityManager.createQuery("FROM Person WHERE firstName = :firstName AND lastName = :lastName")
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();
        return PersonDto.toDto(p);
    }

    public void add(PersonDto person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(PersonDto person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person.toEntity());
        entityManager.getTransaction().commit();
    }

    public void delete(PersonDto person) {
        entityManager.getTransaction().begin();
        entityManager.remove(person.toEntity());
        entityManager.getTransaction().commit();
    }
}
