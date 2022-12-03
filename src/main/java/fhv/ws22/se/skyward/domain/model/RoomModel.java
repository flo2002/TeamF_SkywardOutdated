package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Customer;
import fhv.ws22.se.skyward.persistence.entity.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class RoomModel extends AbstractModel {
    private static final Logger logger = LogManager.getLogger("RoomDto");
    private Integer roomNumber;
    private String roomTypeName;
    private String roomStateName;

    public RoomModel() {
    }
    public RoomModel(Integer roomNumber, String roomTypeName, String roomStateName) {
        setRoomNumber(roomNumber);
        setRoomTypeName(roomTypeName);
        setRoomStateName(roomStateName);
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        int length = String.valueOf(roomNumber).length();
        if (length != 3) {
            throw new IllegalArgumentException("Room number must be 3 digits long.");
        }
        this.roomNumber = roomNumber;
    }
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomStateName() {
        return roomStateName;
    }
    public void setRoomStateName(String roomStateName) {
        if (!(roomStateName.equals("cleaned") || roomStateName.equals("dirty") || roomStateName.equals("out_of_order"))) {
            throw new IllegalArgumentException("Room state must be 'cleaned', 'dirty' or 'out_of_order'.");
        }
        this.roomStateName = roomStateName;
    }

    public RoomDto toDto() {
        logger.info("objects: " + this.toString() + ", msg: Transformation RoomModel to RoomDto");
        return modelMapper.map(this, RoomDto.class);
    }
    public static RoomModel toModel(RoomDto room) {
        logger.info("objects: " + room.toString() + ", msg: Transformation RoomDto to RoomModel");
        ModelMapper mm = new ModelMapper();
        return mm.map(room, RoomModel.class);
    }
    public Room toEntity() {
        logger.info("objects: " + this.toString() + ", msg: Transformation RoomModel to RoomEntity");
        return modelMapper.map(this, Room.class);
    }
    public static RoomModel toModel(Room room) {
        logger.info("objects: " + room.toString() + ", msg: Transformation RoomEntity to RoomModel");
        ModelMapper mm = new ModelMapper();
        return mm.map(room, RoomModel.class);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "roomNumber=" + roomNumber +
                ", roomTypeName='" + roomTypeName + '\'' +
                ", roomStateName='" + roomStateName + '\'' +
                '}';
    }
}
