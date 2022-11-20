package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.InvoiceModel;
import fhv.ws22.se.skyward.persistence.entity.Address;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class InvoiceDto extends AbstractDto {
    private BigInteger invoiceNumber;
    private String companyName;
    private LocalDateTime invoiceDateTime;
    private Boolean isPaid;
    private AddressDto hotelAddress;
    private AddressDto customerAddress;
    private BookingDto booking;

    public InvoiceDto() {
    }
    public InvoiceDto(String companyName, LocalDateTime invoiceDateTime, Boolean isPaid, AddressDto hotelAddress, AddressDto customerAddress, BookingDto booking) {
        this.companyName = companyName;
        this.invoiceDateTime = invoiceDateTime;
        this.isPaid = isPaid;
        this.hotelAddress = hotelAddress;
        this.customerAddress = customerAddress;
        this.booking = booking;
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

    public LocalDateTime getInvoiceDateTime() {
        return invoiceDateTime;
    }
    public void setInvoiceDateTime(LocalDateTime invoiceDateTime) {
        this.invoiceDateTime = invoiceDateTime;
    }

    public AddressDto getHotelAddress() {
        return hotelAddress;
    }
    public void setHotelAddress(AddressDto hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public AddressDto getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress(AddressDto customerAddress) {
        this.customerAddress = customerAddress;
    }

    public BookingDto getBooking() {
        return booking;
    }
    public void setBooking(BookingDto booking) {
        this.booking = booking;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public InvoiceModel toModel() {
        return modelMapper.map(this, InvoiceModel.class);
    }
    public static InvoiceDto toDto(InvoiceModel invoice) {
        ModelMapper mm = new ModelMapper();
        return mm.map(invoice, InvoiceDto.class);
    }

    @Override
    public String toString() {
        return "InvoiceDto{" +
                "invoiceNumber=" + invoiceNumber +
                ", companyName='" + companyName + '\'' +
                ", invoiceDateTime=" + invoiceDateTime +
                ", isPaid=" + isPaid +
                ", hotelAddress=" + hotelAddress +
                ", customerAddress=" + customerAddress +
                '}';
    }
}
