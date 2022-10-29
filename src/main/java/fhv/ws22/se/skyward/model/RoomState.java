package fhv.ws22.se.skyward.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class RoomState extends AbstractEntity {
    private String name;
    @OneToMany(mappedBy = "roomState", cascade = CascadeType.ALL, orphanRemoval = true)
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
}

    /*FREE,
    FREE_FOR_CLEANING,
    OCCUPIED,
    OUT_OF_ORDER*/