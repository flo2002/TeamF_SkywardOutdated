package fhv.ws22.se.skyward.model;

import fhv.ws22.se.skyward.model.DTOs.RoomStateDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class RoomState extends AbstractEntity {
    private String name;
    @OneToMany(mappedBy = "roomState")
    private List<Room> rooms;

    public RoomState() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}

    /*FREE,
    FREE_FOR_CLEANING,
    OCCUPIED,
    OUT_OF_ORDER*/