package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class RoomDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("RoomDto");
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
        logger.info("objects: " + this.toString() + ", msg: Transformation Room Entity to RoomDto.");
        return modelMapper.map(this, Room.class);
    }
    public static RoomDto toDto(Room room) {
        logger.info("objects: " + room.toString() + ", msg: Transformation RoomDto to Room Entity");
        ModelMapper mm = new ModelMapper();
        return mm.map(room, RoomDto.class);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "roomNumber=" + roomNumber +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", roomTypePrice=" + roomTypePrice +
                ", roomStateName='" + roomStateName + '\'' +
                '}';
    }
}
