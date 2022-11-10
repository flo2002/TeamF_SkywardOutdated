package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class RoomState extends AbstractEntity {
    //@Column(unique = true)
    private String name;

    public RoomState() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoomState{" +
                "name='" + name + '\'' +
                '}';
    }
}

    /*FREE,
    FREE_FOR_CLEANING,
    OCCUPIED,
    OCCUPIED_FOR_CLEANING,
    OUT_OF_ORDER*/