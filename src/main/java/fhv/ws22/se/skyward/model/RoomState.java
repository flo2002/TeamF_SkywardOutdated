package fhv.ws22.se.skyward.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class RoomState extends AbstractEntity {
    //@Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "roomState")
    private List<Room> rooms1;

    public RoomState() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms1;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms1 = rooms;
    }

    @Override
    public String toString() {
        return "RoomState{" +
                "name='" + name + '\'' +
                ", rooms=" + rooms1 +
                '}';
    }
}

    /*FREE,
    FREE_FOR_CLEANING,
    OCCUPIED,
    OUT_OF_ORDER*/