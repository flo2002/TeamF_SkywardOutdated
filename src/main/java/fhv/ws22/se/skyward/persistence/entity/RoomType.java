package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "price"}))
public class RoomType extends AbstractEntity {
    private String name;
    private BigDecimal price;

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

    @Override
    public String toString() {
        return "RoomType{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
