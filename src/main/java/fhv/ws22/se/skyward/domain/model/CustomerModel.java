package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.persistence.entity.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerModel extends AbstractModel {
    private static final Logger logger = LogManager.getLogger("CustomerDto");
    private String firstName;
    private String lastName;
    private AddressModel address;

    public CustomerModel() {
    }

    public CustomerModel(String firstName, String lastName, AddressModel address) throws NameNotValidException {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) throws NameNotValidException {
        String regex = "^[A-Z][a-zA-z ]{1,29}$"; // first letter of every word must be uppercase
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(firstName);
        if(!matcher.matches()){
            logger.error("First name is not valid");
            throw new NameNotValidException("First name not valid");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) throws NameNotValidException {
        String regex = "^[A-Z][a-zA-z ]{1,29}$"; // first letter of every word must be uppercase
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(lastName);
        if(!matcher.matches()){
            logger.error("Last name is not valid");
            throw new NameNotValidException("Last name not valid");
        }
        this.lastName = lastName;
    }

    public AddressModel getAddress() {
        return address;
    }
    public void setAddress(AddressModel address) {
        this.address = address;
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
                ", address=" + address +
                '}';
    }
}
