package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.persistence.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

public class CustomerModel extends AbstractModel {
    private static final Logger logger = LogManager.getLogger("CustomerDto");
    private String firstName;
    private String lastName;

    public CustomerModel() {
    }
    public CustomerModel(String firstName, String lastName) {
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

    public Customer toModel() {
        logger.info("objects: " + this.toString() + ", msg: Transformation Customer Model to CustomerDto.");
        return modelMapper.map(this, Customer.class);
    }

    public static CustomerModel toDto(Customer customer) {
        logger.info("objects: " + customer.toString() + ", msg: Transformation CustomerDto to Customer Model");
        ModelMapper mm = new ModelMapper();
        return mm.map(customer, CustomerModel.class);
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
