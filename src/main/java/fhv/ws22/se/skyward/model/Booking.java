package fhv.ws22.se.skyward.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"checkInDateTime", "checkOutDateTime", "room_id", "person_id"}))
public class Booking extends AbstractEntity {
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private Boolean isCheckedIn;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "booking_person",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Customer> customers;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "booking_room",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;

    public Booking() {
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

    public List<Customer> getPersons() {
        return customers;
    }
    public void setPersons(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}