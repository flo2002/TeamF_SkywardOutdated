package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.model.PersonConverter;
import fhv.ws22.se.skyward.persistence.model.PersonEntity;
import fhv.ws22.se.skyward.view.model.PersonView;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PersonBroker extends BrokerBase<PersonView> {
    private final EntityManager entityManager;

    public PersonBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<PersonView> getAll() {
        List<PersonEntity> personEntities = (List<PersonEntity>) entityManager.createQuery("FROM Person").getResultList();

        List<Person> persons = new ArrayList<Person>();
        for (PersonEntity personEntity : personEntities) {
            Person person = PersonConverter.fromPersonEntityToPerson(personEntity);
            persons.add(person);
        }

        List<PersonView> personViews = new ArrayList<PersonView>();
        for (Person person : persons) {
            PersonView personView = PersonConverter.fromPersonViewToPersonEntity(person);
            personViews.add(personView);
        }

        return personViews;
    }

    public void add(PersonView personView) {
        Person person = PersonConverter.fromPersonViewToPerson(personView);
        PersonEntity personEntity = PersonConverter.fromPersonToPersonEntity(person);

        entityManager.getTransaction().begin();
        entityManager.persist(personEntity);
        entityManager.getTransaction().commit();
    }

    public void update(PersonView personView) {
        Person person = PersonConverter.fromPersonViewToPerson(personView);
        PersonEntity personEntity = PersonConverter.fromPersonToPersonEntity(person);

        entityManager.getTransaction().begin();
        entityManager.merge(personEntity);
        entityManager.getTransaction().commit();
    }

    public void delete(PersonView personView) {
        Person person = PersonConverter.fromPersonViewToPerson(personView);
        PersonEntity personEntity = PersonConverter.fromPersonToPersonEntity(person);

        entityManager.getTransaction().begin();
        entityManager.remove(personEntity);
        entityManager.getTransaction().commit();
    }
}
