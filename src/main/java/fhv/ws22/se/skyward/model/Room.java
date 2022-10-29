package fhv.ws22.se.skyward.model;

import fhv.ws22.se.skyward.model.DTOs.RoomDto;
import fhv.ws22.se.skyward.model.DTOs.RoomStateDto;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room extends AbstractEntity {
    private int roomNumber;
    @ManyToOne
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
    @ManyToOne
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

    public RoomDto toDto() {
        return new RoomDto(roomNumber, roomType.toDto(), roomState.toDto());
    }
}
