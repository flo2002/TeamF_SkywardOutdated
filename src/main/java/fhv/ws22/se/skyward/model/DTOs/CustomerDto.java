package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Customer;
import org.modelmapper.ModelMapper;

public class CustomerDto extends AbstractDto {
    private String firstName;
    private String lastName;

    public CustomerDto() {
    }
    public CustomerDto(String firstName, String lastName) {
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

    public Customer toEntity() {
        return modelMapper.map(this, Customer.class);
    }

    public static CustomerDto toDto(Customer customer) {
        ModelMapper mm = new ModelMapper();
        return mm.map(customer, CustomerDto.class);
    }
}
