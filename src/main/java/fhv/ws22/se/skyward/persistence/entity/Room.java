package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Room extends AbstractEntity {
    //@Column(unique = true)
    private int roomNumber;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "roomType_id")
    private RoomType roomType;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "roomState_id")
    private RoomState roomState;

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

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", roomState=" + roomState +
                '}';
    }
}
