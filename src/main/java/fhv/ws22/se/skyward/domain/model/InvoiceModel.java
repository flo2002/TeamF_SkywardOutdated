package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.persistence.entity.Invoice;
import org.modelmapper.ModelMapper;

public class InvoiceModel extends AbstractModel {
    private String companyName;
    private AddressModel hotelAddress;
    private BookingModel booking;
    private Boolean isPaid;

    public InvoiceModel() {
    }
    public InvoiceModel(String companyName, AddressModel address, BookingModel booking, Boolean isPaid) {
        this.companyName = companyName;
        this.hotelAddress = address;
        this.booking = booking;
        this.isPaid = isPaid;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
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
        return "InvoiceDto{" +
                "companyName='" + companyName + '\'' +
                ", hotelAddress=" + hotelAddress +
                ", booking=" + booking +
                ", isPaid=" + isPaid +
                '}';
    }
}
