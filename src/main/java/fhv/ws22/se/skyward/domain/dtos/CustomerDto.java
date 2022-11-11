package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.CustomerModel;
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

    public CustomerModel toModel() {
        logger.info("objects: " + this.toString() + ", msg: Transformation CustomerDto to CustomerModel.");
        return modelMapper.map(this, CustomerModel.class);
    }
    public static CustomerDto toDto(CustomerModel customer) {
        logger.info("objects: " + customer.toString() + ", msg: Transformation CustomerModel to CustomerDto");
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
