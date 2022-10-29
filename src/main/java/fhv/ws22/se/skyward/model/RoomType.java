package fhv.ws22.se.skyward.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class RoomType extends AbstractEntity {
    private String name;
    private BigDecimal price;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    public RoomType() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
