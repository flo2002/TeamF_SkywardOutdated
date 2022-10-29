package fhv.ws22.se.skyward.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Booking extends AbstractEntity {
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    @ManyToMany
    @JoinTable(name = "booking_person",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> persons;
    @ManyToMany
    @JoinTable(
            name = "booking_room",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;
}
