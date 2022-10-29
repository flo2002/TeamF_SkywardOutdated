package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Person;

public class PersonDto extends DTOMarker {
    private String firstName;
    private String lastName;

    public PersonDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Person toPerson() {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        return person;
    }
}
