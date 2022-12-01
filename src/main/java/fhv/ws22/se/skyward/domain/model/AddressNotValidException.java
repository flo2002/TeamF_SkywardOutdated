package fhv.ws22.se.skyward.domain.model;

public class AddressNotValidException extends Exception{
    public AddressNotValidException(String message){
        super(message);
    }
}
