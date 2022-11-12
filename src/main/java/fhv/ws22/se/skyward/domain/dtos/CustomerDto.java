package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.CustomerModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

public class CustomerDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("CustomerDto");
    private String firstName;
    private String lastName;
    private String addressStreet;
    private String addressHouseNumber;
    private String addressZipCode;
    private String addressCity;
    private String addressCountry;

    public CustomerDto() {
    }
    public CustomerDto(String firstName, String lastName, String addressStreet, String addressHouseNumber, String addressZipCode, String addressCity, String addressCountry) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressStreet = addressStreet;
        this.addressHouseNumber = addressHouseNumber;
        this.addressZipCode = addressZipCode;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
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

    public String getAddressStreet() {
        return addressStreet;
    }
    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressHouseNumber() {
        return addressHouseNumber;
    }
    public void setAddressHouseNumber(String addressHouseNumber) {
        this.addressHouseNumber = addressHouseNumber;
    }

    public String getAddressZipCode() {
        return addressZipCode;
    }
    public void setAddressZipCode(String addressZipCode) {
        this.addressZipCode = addressZipCode;
    }

    public String getAddressCity() {
        return addressCity;
    }
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressCountry() {
        return addressCountry;
    }
    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
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
