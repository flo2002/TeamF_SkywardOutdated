package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Booking;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDto extends AbstractDto {
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

    public List<CustomerDto> getPersons() {
        return customers;
    }
    public void setPersons(List<CustomerDto> persons) {
        this.customers = persons;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }
    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    public Booking toEntity() {
        return modelMapper.map(this, Booking.class);
    }
    public static BookingDto toDto(Booking booking) {
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
