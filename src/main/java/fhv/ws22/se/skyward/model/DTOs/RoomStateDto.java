package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.RoomState;

public class RoomStateDto extends DTOMarker {
    private String name;

    public RoomStateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public RoomState toRoomState() {
        RoomState roomState = new RoomState();
        roomState.setName(name);
        return roomState;
    }
}
