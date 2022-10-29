package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Room;

public class RoomDto extends DTOMarker {
    private int roomNumber;
    private RoomTypeDto roomType;
    private RoomStateDto roomState;

    public RoomDto(int roomNumber, RoomTypeDto roomType, RoomStateDto roomState) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomState = roomState;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomTypeDto getRoomType() {
        return roomType;
    }
    public void setRoomType(RoomTypeDto roomType) {
        this.roomType = roomType;
    }

    public RoomStateDto getRoomState() {
        return roomState;
    }
    public void setRoomState(RoomStateDto roomState) {
        this.roomState = roomState;
    }

    public Room toRoom() {
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType.toRoomType());
        room.setRoomState(roomState.toRoomState());
        return room;
    }
}
