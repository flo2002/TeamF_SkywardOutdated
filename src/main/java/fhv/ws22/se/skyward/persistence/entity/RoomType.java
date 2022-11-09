package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "price"}))
public class RoomType extends AbstractEntity {
    private String name;
    private BigDecimal price;

    @OneToMany(mappedBy = "roomType")
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

    @Override
    public String toString() {
        return "RoomType{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", rooms=" + rooms +
                '}';
    }
}
