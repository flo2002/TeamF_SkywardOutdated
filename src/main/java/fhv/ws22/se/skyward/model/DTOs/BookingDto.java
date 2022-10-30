package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Booking;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDto extends AbstractDto {
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private List<PersonDto> persons;
    private List<RoomDto> rooms;

    public BookingDto() {
    }
    public BookingDto(LocalDateTime checkInDateTime, LocalDateTime checkOutDateTime, List<PersonDto> persons, List<RoomDto> rooms) {
        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
        this.persons = persons;
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

    public List<PersonDto> getPersons() {
        return persons;
    }
    public void setPersons(List<PersonDto> persons) {
        this.persons = persons;
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
}
