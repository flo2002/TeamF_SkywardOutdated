package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Booking extends AbstractEntity {
    @Column(unique = true)
    private BigInteger bookingNumber;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;
    private Boolean isCheckedIn;
    @ManyToMany
    @JoinTable(name = "booking_customer",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> customers;
    @ManyToMany
    @JoinTable(name = "booking_room",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;
    @OneToMany(mappedBy = "booking")
    private List<ChargeableItem> chargeableItems;
    @OneToMany(mappedBy = "booking")
    private List<Invoice> invoices;

    public Booking() {
    }

    public BigInteger getBookingNumber() {
        if (bookingNumber == null) {
            setBookingNumber(AbstractEntity.getBookingNum());
        }
        return bookingNumber;
    }
    public void setBookingNumber(BigInteger bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public LocalDateTime getCheckInDateTime() {
        return checkInDateTime;
    }
    public void setCheckInDateTime(LocalDateTime checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public LocalDateTime getCheckOutDateTime() {
        return checkOutDateTime;
    }
    public void setCheckOutDateTime(LocalDateTime checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }

    public Boolean getIsCheckedIn() {
        return isCheckedIn;
    }
    public void setIsCheckedIn(Boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<ChargeableItem> getChargeableItems() {
        return chargeableItems;
    }
    public void setChargeableItems(List<ChargeableItem> chargeableItems) {
        this.chargeableItems = chargeableItems;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }
    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "checkInDateTime=" + checkInDateTime +
                ", checkOutDateTime=" + checkOutDateTime +
                ", isCheckedIn=" + isCheckedIn +
                ", customers=" + customers +
                ", rooms=" + rooms +
                ", chargeableItems=" + chargeableItems +
                ", invoices=" + invoices +
                '}';
    }
}
