package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

public class BookingModel extends AbstractModel {
    private static final Logger logger = LogManager.getLogger("BookingDto");
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private Boolean isCheckedIn;
    private List<CustomerModel> customers;
    private List<RoomModel> rooms;

    public BookingModel() {
    }
    public BookingModel(LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime, Boolean isCheckedIn, List<CustomerModel> customers, List<RoomModel> rooms) {
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
        logger.info("objects: " + this.toString() + ", msg: Transformation Booking Model to BookingDto.");
        return modelMapper.map(this, BookingDto.class);
    }
    public static BookingModel toModel(BookingDto booking) {
        logger.info("objects: " + booking.toString() + ", msg: Transformation BookingDto to Booking Model");
        ModelMapper mm = new ModelMapper();
        return mm.map(booking, BookingModel.class);
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
