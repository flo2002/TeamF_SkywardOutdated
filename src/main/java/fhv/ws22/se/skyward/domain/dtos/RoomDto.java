package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.RoomModel;
import javafx.scene.control.CheckBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class RoomDto extends AbstractDto {
    private static final Logger logger = LogManager.getLogger("RoomDto");
    private Integer roomNumber;
    private String roomTypeName;
    private String roomStateName;

    public RoomDto() {
    }
    public RoomDto(Integer roomNumber, String roomTypeName, String roomStateName) {
        this.roomNumber = roomNumber;
        this.roomTypeName = roomTypeName;
        this.roomStateName = roomStateName;
    }

    public Integer getRoomNumber() {
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

    public String getRoomStateName() {
        return roomStateName;
    }
    public void setRoomStateName(String roomStateName) {
        this.roomStateName = roomStateName;
    }

    public RoomModel toModel() {
        logger.info("objects: " + this.toString() + ", msg: Transformation RoomDto to RoomModel");
        return modelMapper.map(this, RoomModel.class);
    }
    public static RoomDto toDto(RoomModel room) {
        logger.info("objects: " + room.toString() + ", msg: Transformation RoomModel to RoomDto");
        ModelMapper mm = new ModelMapper();
        return mm.map(room, RoomDto.class);
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
