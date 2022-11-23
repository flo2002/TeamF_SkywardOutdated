package fhv.ws22.se.skyward.domain.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingDateNotValidException extends Exception {

    public BookingDateNotValidException(String message) {
        super(message);
    }
}
