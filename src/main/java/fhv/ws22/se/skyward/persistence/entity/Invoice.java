package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.List;

@Entity
public class Invoice extends AbstractEntity {
    @Column(unique = true)
    private BigInteger invoiceNumber;
    private String companyName;
    private Boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address hotelAddress;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Invoice() {
    }

    public BigInteger getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(BigInteger invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Address getHotelAddress() {
        return hotelAddress;
    }
    public void setHotelAddress(Address hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", companyName='" + companyName + '\'' +
                ", hotelAddress=" + hotelAddress +
                ", isPaid=" + isPaid +
                ", booking=" + booking +
                '}';
    }
}
