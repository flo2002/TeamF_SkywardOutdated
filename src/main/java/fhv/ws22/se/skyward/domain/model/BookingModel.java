package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class BookingModel extends AbstractModel {
    private static final Logger logger = LogManager.getLogger("BookingDto");
    private BigInteger bookingNumber;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private Boolean isCheckedIn;
    private List<CustomerModel> customers;
    private List<RoomModel> rooms;

    public BookingModel() {
    }

    public BookingModel(LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime, Boolean isCheckedIn, List<CustomerModel> customers, List<RoomModel> rooms) throws Exception {
        setCheckInDateTime(checkInDateTime);
        setCheckOutDateTime(checkOutDateTime);
        setIsCheckedIn(isCheckedIn);
        setCustomers(customers);
        setRooms(rooms);
    }

    public BigInteger getBookingNumber() {
        return bookingNumber;
    }
    public void setBookingNumber(BigInteger bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public LocalDateTime getCheckInDateTime() {
        return checkInDateTime;
    }
    public void setCheckInDateTime(LocalDateTime checkInDateTime) {
        // validation checks here is too much trouble, because old bookings might have invalid dates --> solution: check in view
        this.checkInDateTime = checkInDateTime;
    }

    public LocalDateTime getCheckOutDateTime() {
        return checkOutDateTime;
    }
    public void setCheckOutDateTime(LocalDateTime checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }

    public Boolean getIsCheckedIn() {
        return isCheckedIn;
    }
    public void setIsCheckedIn(Boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
    }

    public List<CustomerModel> getCustomers() {
        return customers;
    }
    public void setCustomers(List<CustomerModel> customers) {
        this.customers = customers;
    }

    public List<RoomModel> getRooms() {
        return rooms;
    }
    public void setRooms(List<RoomModel> rooms) {
        this.rooms = rooms;
    }


    public BookingDto toDto() {
        logger.info("objects: " + this.toString() + ", msg: Transformation BookingModel to BookingDto.");
        return modelMapper.map(this, BookingDto.class);
    }
    public static BookingModel toModel(BookingDto booking) {
        logger.info("objects: " + booking.toString() + ", msg: Transformation BookingDto to BookingModel");
        ModelMapper mm = new ModelMapper();
        return mm.map(booking, BookingModel.class);
    }
    public Booking toEntity() {
        logger.info("objects: " + this.toString() + ", msg: Transformation BookingModel to BookingEntity.");
        return modelMapper.map(this, Booking.class);
    }
    public static BookingModel toModel(Booking booking) {
        logger.info("objects: " + booking.toString() + ", msg: Transformation BookingEntity to BookingModel");
        ModelMapper mm = new ModelMapper();
        return mm.map(booking, BookingModel.class);
    }

    @Override
    public String toString() {
        return "BookingModel{" +
                "bookingNumber=" + bookingNumber +
                ", checkInDateTime=" + checkInDateTime +
                ", checkOutDateTime=" + checkOutDateTime +
                ", isCheckedIn=" + isCheckedIn +
                ", customers=" + customers +
                ", rooms=" + rooms +
                '}';
    }
}
