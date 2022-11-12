package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.InvoiceModel;
import fhv.ws22.se.skyward.persistence.entity.Address;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import org.modelmapper.ModelMapper;

public class InvoiceDto extends AbstractDto {
    private String companyName;
    private Address hotelAddress;
    private Booking booking;
    private Boolean isPaid;

    public InvoiceDto() {
    }
    public InvoiceDto(String companyName, Address hotelAddress, Booking booking, Boolean isPaid) {
        this.companyName = companyName;
        this.hotelAddress = hotelAddress;
        this.booking = booking;
        this.isPaid = isPaid;
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
                "companyName='" + companyName + '\'' +
                ", hotelAddress=" + hotelAddress +
                ", booking=" + booking +
                ", isPaid=" + isPaid +
                '}';
    }
}
