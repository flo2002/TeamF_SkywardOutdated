package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.persistence.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

public class CustomerDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("CustomerDto");
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
        logger.info("objects: " + this.toString() + ", msg: Transformation Customer Entity to CustomerDto.");
        return modelMapper.map(this, Customer.class);
    }

    public static CustomerDto toDto(Customer customer) {
        logger.info("objects: " + customer.toString() + ", msg: Transformation CustomerDto to Customer Entity");
        ModelMapper mm = new ModelMapper();
        return mm.map(customer, CustomerDto.class);
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}