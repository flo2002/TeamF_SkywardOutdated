package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;

@Entity
public class Invoice extends AbstractEntity {
    private String companyName;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address hotelAddress;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
    private Boolean isPaid;

    public Invoice() {
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "companyName='" + companyName + '\'' +
                ", hotelAddress=" + hotelAddress +
                ", booking=" + booking +
                ", isPaid=" + isPaid +
                '}';
    }
}
