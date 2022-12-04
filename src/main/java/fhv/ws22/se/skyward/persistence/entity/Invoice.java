package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
public class Invoice extends AbstractEntity {
    @Column(unique = true)
    private BigInteger invoiceNumber;
    private String companyName;
    private LocalDateTime invoiceDateTime;
    private Boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "hotelInvoices_id")
    private Address hotelAddress;
    @ManyToOne
    @JoinColumn(name = "customerInvoices_id")
    private Address customerAddress;
    @ManyToOne
    @JoinColumn(name = "customers")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "billed_customer_id")
    private Customer billedCustomer;

    public Invoice() {
    }

    public BigInteger getInvoiceNumber() {
        if (invoiceNumber == null) {
            setInvoiceNumber(AbstractEntity.getInvoiceNum());
        }
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

    public LocalDateTime getInvoiceDateTime() {
        return invoiceDateTime;
    }
    public void setInvoiceDateTime(LocalDateTime invoiceDateTime) {
        this.invoiceDateTime = invoiceDateTime;
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

    public Address getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(Address customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Customer getBilledCustomer() {
        return billedCustomer;
    }
    public void setBilledCustomer(Customer billedCustomer) {
        this.billedCustomer = billedCustomer;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber=" + invoiceNumber +
                ", companyName='" + companyName + '\'' +
                ", invoiceDateTime=" + invoiceDateTime +
                ", isPaid=" + isPaid +
                ", hotelAddress=" + hotelAddress +
                ", customerAddress=" + customerAddress +
                ", booking=" + booking +
                ", billedCustomer=" + billedCustomer +
                '}';
    }
}
