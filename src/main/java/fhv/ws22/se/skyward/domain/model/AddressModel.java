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
    private Integer houseNumber;
    private Integer zipCode;
    private String city;
    private String country;

    public AddressModel() {
    }
    public AddressModel(String street, Integer houseNumber, Integer zipCode, String city, String country) throws AddressNotValidException  {
        setStreet(street);
        setHouseNumber(houseNumber);
        setZipCode(zipCode);
        setCity(city);
        setCountry(country);
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) throws AddressNotValidException{
        String regex = "^[A-Za-z]{2,64}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(street);

        if(!matcher.matches()) {
            logger.error("Street is not valid");
            throw new AddressNotValidException("Street is not Valid");
        }
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(Integer houseNumber) throws AddressNotValidException{
        String regex = "^\\d+(\\s|-)?\\w*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(houseNumber.toString());

        if(!matcher.matches()){
            logger.error("House is not valid");
            throw new AddressNotValidException("House is not valid");
        }
        this.houseNumber = houseNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }
    public void setZipCode(Integer zipCode) throws AddressNotValidException{
        String regex = "^[0-9]{4,5}(?:-[0-9]{1,4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zipCode.toString());
        if(!matcher.matches()){
            logger.error("Zip Code number is not valid");
            throw new AddressNotValidException("Zip Code number is not valid");
        }
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) throws AddressNotValidException{
        String regex = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(city);
        if(!matcher.matches()){
            logger.error("City not valid");
            throw new AddressNotValidException("City not valid");
        }
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) throws AddressNotValidException {
        String regex = "^[A-Z][a-z]+( [A-Z][a-z]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(country);
        if(!matcher.matches()){
            logger.error("Country not valid");
            throw new AddressNotValidException("Country not valid");
        }
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
        return "AddressModel{" +
                "street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
