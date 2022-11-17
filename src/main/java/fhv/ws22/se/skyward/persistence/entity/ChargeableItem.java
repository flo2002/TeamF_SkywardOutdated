package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class ChargeableItem extends AbstractEntity {
    private String name;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


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

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Invoice getInvoice() {
        return invoice;
    }
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


    @Override
    public String toString() {
        return "ChargeableItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
