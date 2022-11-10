package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
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

    public CustomerDto toDto() {
        logger.info("objects: " + this.toString() + ", msg: Transformation CustomerModel to CustomerDto.");
        return modelMapper.map(this, CustomerDto.class);
    }
    public static CustomerModel toModel(CustomerDto customer) {
        logger.info("objects: " + customer.toString() + ", msg: Transformation CustomerDto to CustomerModel");
        ModelMapper mm = new ModelMapper();
        return mm.map(customer, CustomerModel.class);
    }
    public Customer toEntity() {
        logger.info("objects: " + this.toString() + ", msg: Transformation CustomerModel to CustomerEntity.");
        return modelMapper.map(this, Customer.class);
    }
    public static CustomerModel toModel(Customer customer) {
        logger.info("objects: " + customer.toString() + ", msg: Transformation CustomerEntity to CustomerModel");
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
