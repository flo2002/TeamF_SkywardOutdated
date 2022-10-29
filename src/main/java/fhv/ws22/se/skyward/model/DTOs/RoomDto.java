package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Room;
import org.modelmapper.ModelMapper;

public class RoomDto extends AbstractDto {
    private int roomNumber;
    private RoomTypeDto roomType;
    private RoomStateDto roomState;

    public RoomDto() {
    }
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

    public Room toEntity() {
        return modelMapper.map(this, Room.class);
    }
    public static RoomDto toDto(Room room) {
        ModelMapper mm = new ModelMapper();
        return mm.map(room, RoomDto.class);
    }
}
