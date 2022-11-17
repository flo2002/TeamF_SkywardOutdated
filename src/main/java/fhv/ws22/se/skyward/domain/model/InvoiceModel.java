package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.persistence.entity.Invoice;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;

public class InvoiceModel extends AbstractModel {
    private BigInteger invoiceNumber;
    private String companyName;
    private Boolean isPaid;
    private AddressModel hotelAddress;
    private BookingModel booking;

    public InvoiceModel() {
    }
    public InvoiceModel(String companyName, Boolean isPaid, AddressModel address, BookingModel booking) {
        setCompanyName(companyName);
        setIsPaid(isPaid);
        setHotelAddress(address);
        setBooking(booking);
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

    public AddressModel getHotelAddress() {
        return hotelAddress;
    }
    public void setHotelAddress(AddressModel hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public BookingModel getBooking() {
        return booking;
    }
    public void setBooking(BookingModel booking) {
        this.booking = booking;
    }


    public InvoiceDto toDto() {
        return modelMapper.map(this, InvoiceDto.class);
    }
    public static InvoiceModel toModel(InvoiceDto invoice) {
        ModelMapper mm = new ModelMapper();
        return mm.map(invoice, InvoiceModel.class);
    }
    public Invoice toEntity() {
        return modelMapper.map(this, Invoice.class);
    }
    public static InvoiceModel toModel(Invoice invoice) {
        ModelMapper mm = new ModelMapper();
        return mm.map(invoice, InvoiceModel.class);
    }

    @Override
    public String toString() {
        return "InvoiceModel{" +
                "invoiceNumber=" + invoiceNumber +
                ", companyName='" + companyName + '\'' +
                ", isPaid=" + isPaid +
                ", hotelAddress=" + hotelAddress +
                ", booking=" + booking +
                '}';
    }
}
