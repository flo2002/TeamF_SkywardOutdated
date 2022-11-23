package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.persistence.entity.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.NotImplementedYetException;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.query.IllegalQueryOperationException;
import org.modelmapper.ModelMapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressModel extends AbstractModel {
    private static final Logger logger = LogManager.getLogger("AddressModel");
    private String street;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String country;

    public AddressModel() {
    }
    public AddressModel(String street, String houseNumber, String zipCode, String city, String country) {
        setStreet(street);
        setHouseNumber(houseNumber);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        String regex = "^[A-Za-z]{2,64}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(street);

        if(!matcher.matches()) {
            logger.error("Street is not valid");
        }
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public AbstractDto toDto() {
        // not implemented, because view does not need the address, but AbstractModel requires it
        throw new NotYetImplementedException();
    }

    public Address toEntity() {
        ModelMapper mm = new ModelMapper();
        return mm.map(this, Address.class);
    }
    public static AddressModel toModel(Address address) {
        ModelMapper mm = new ModelMapper();
        return mm.map(address, AddressModel.class);
    }

    @Override
    public String toString() {
        return "AddressModel [city=" + city + ", country=" + country + ", houseNumber=" + houseNumber + ", street="
                + street + ", zipCode=" + zipCode + "]";
    }
}
