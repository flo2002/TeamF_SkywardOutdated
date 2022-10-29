package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Room;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class RoomDto extends AbstractDto {
    private int roomNumber;
    private String roomTypeName;
    private BigDecimal roomTypePrice;
    private String roomStateName;

    public RoomDto() {
    }
    public RoomDto(int roomNumber, String roomTypeName, BigDecimal roomTypePrice, String roomStateName) {
        this.roomNumber = roomNumber;
        this.roomTypeName = roomTypeName;
        this.roomTypePrice = roomTypePrice;
        this.roomStateName = roomStateName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }
    public BigDecimal getRoomTypePrice() {
        return roomTypePrice;
    }
    public void setRoomTypePrice(BigDecimal roomTypePrice) {
        this.roomTypePrice = roomTypePrice;
    }

    public String getRoomStateName() {
        return roomStateName;
    }
    public void setRoomStateName(String roomStateName) {
        this.roomStateName = roomStateName;
    }

    public Room toEntity() {
        return modelMapper.map(this, Room.class);
    }
    public static RoomDto toDto(Room room) {
        ModelMapper mm = new ModelMapper();
        return mm.map(room, RoomDto.class);
    }
}
