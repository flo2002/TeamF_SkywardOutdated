package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ChargeableItem extends AbstractEntity {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;


    public ChargeableItem() {
    }


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

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }


    @Override
    public String toString() {
        return "ChargeableItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", booking=" + booking +
                '}';
    }
}
