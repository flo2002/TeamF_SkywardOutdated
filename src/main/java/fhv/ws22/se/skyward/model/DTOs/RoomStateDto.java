package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.RoomState;
import org.modelmapper.ModelMapper;

public class RoomStateDto extends AbstractDto {
    private String name;

    public RoomStateDto() {
    }
    public RoomStateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public RoomState toEntity() {
        return modelMapper.map(this, RoomState.class);
    }
    public static RoomStateDto toDto(RoomState roomState) {
        ModelMapper mm = new ModelMapper();
        return mm.map(roomState, RoomStateDto.class);
    }
}
