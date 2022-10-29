package fhv.ws22.se.skyward.model;

import fhv.ws22.se.skyward.model.DTOs.PersonDto;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person extends AbstractEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(mappedBy = "persons")
    private List<Booking> bookings;


    public Person() {
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

    public PersonDto toDTO() {
        return new PersonDto(firstName, lastName);
    }
}
