package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.BookingModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class BookingDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("BookingDto");
    private BigInteger bookingNumber;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private Boolean isCheckedIn;
    private List<CustomerDto> customers;
    private List<RoomDto> rooms;

    public BookingDto() {
    }
    public BookingDto(LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime, Boolean isCheckedIn, List<CustomerDto> customers, List<RoomDto> rooms) {
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.isCheckedIn = isCheckedIn;
        this.customers = customers;
        this.rooms = rooms;
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

    public List<CustomerDto> getCustomers() {
        return customers;
    }
    public void setCustomers(List<CustomerDto> customers) {
        this.customers = customers;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }
    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }


    public BookingModel toModel() {
        logger.info("objects: " + this.toString() + ", msg: Transformation BookingDto to BookingModel.");
        return modelMapper.map(this, BookingModel.class);
    }
    public static BookingDto toDto(BookingModel booking) {
        logger.info("objects: " + booking.toString() + ", msg: Transformation BookingModel to BookingDto");
        ModelMapper mm = new ModelMapper();
        return mm.map(booking, BookingDto.class);
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "bookingNumber=" + bookingNumber +
                ", checkInDateTime=" + checkInDateTime +
                ", checkOutDateTime=" + checkOutDateTime +
                ", isCheckedIn=" + isCheckedIn +
                ", customers=" + customers +
                ", rooms=" + rooms +
                '}';
    }
}
