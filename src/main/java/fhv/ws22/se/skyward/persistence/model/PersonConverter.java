package fhv.ws22.se.skyward.persistence.model;

import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.view.model.PersonView;

import java.util.List;
import java.util.function.Consumer;

public class PersonConverter {
    public static Person fromPersonViewToPerson(PersonView personView) {
        Person person = new Person();
        person.setFirstName(personView.getFirstName());
        person.setLastName(personView.getLastName());
        return person;
    }

    public static PersonEntity fromPersonToPersonEntity(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setFirstName(person.getFirstName());
        personEntity.setLastName(person.getLastName());
        return personEntity;
    }

    public static Person fromPersonEntityToPerson(PersonEntity personEntity) {
        Person person = new Person();
        person.setFirstName(personEntity.getFirstName());
        person.setLastName(personEntity.getLastName());
        return person;
    }

    public static PersonView fromPersonViewToPersonEntity(Person person) {
        PersonView personView = new PersonView();
        personView.setFirstName(person.getFirstName());
        personView.setLastName(person.getLastName());
        return personView;
    }
}
