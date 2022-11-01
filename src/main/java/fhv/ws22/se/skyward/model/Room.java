package fhv.ws22.se.skyward.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Room extends AbstractEntity {
    //@Column(unique = true)
    private int roomNumber;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "roomState_id")
    private RoomState roomState;
    @ManyToMany(mappedBy = "rooms")
    private List<Booking> bookings;

    public Room() {}

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomState getRoomState() {
        return roomState;
    }
    public void setRoomState(RoomState roomState) {
        this.roomState = roomState;
    }

    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
