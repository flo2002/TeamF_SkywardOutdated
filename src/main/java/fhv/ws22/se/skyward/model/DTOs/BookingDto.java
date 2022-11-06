package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Booking;
import fhv.ws22.se.skyward.model.Customer;
import fhv.ws22.se.skyward.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class BookingDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("BookingDto");
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

    public Booking toEntity() {
        logger.info("objects: " + this.toString() + ", msg: Transformation Booking Entity to BookingDto.");
        return modelMapper.map(this, Booking.class);
    }
    public static BookingDto toDto(Booking booking) {
        logger.info("objects: " + booking.toString() + ", msg: Transformation BookingDto to Booking Entity");
        ModelMapper mm = new ModelMapper();
        return mm.map(booking, BookingDto.class);
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "checkInDateTime=" + checkInDateTime +
                ", checkOutDateTime=" + checkOutDateTime +
                ", isCheckedIn=" + isCheckedIn +
                ", customers=" + customers +
                ", rooms=" + rooms +
                '}';
    }
}
