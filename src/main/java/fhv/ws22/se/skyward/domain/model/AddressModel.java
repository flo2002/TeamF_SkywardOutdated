package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.persistence.entity.Address;
import org.hibernate.NotImplementedYetException;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.query.IllegalQueryOperationException;
import org.modelmapper.ModelMapper;

public class AddressModel extends AbstractModel {
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
