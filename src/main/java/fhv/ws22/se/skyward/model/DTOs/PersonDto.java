package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Person;
import org.modelmapper.ModelMapper;

public class PersonDto extends AbstractDto {
    private String firstName;
    private String lastName;

    public PersonDto() {
    }
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

    public Person toEntity() {
        return modelMapper.map(this, Person.class);
    }

    public static PersonDto toDto(Person person) {
        ModelMapper mm = new ModelMapper();
        return mm.map(person, PersonDto.class);
    }
}
