package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.InvoiceDto;
import fhv.ws22.se.skyward.persistence.entity.Invoice;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;

public class InvoiceModel extends AbstractModel {
    private BigInteger invoiceNumber;
    private String companyName;
    private AddressModel hotelAddress;
    private Boolean isPaid;

    public InvoiceModel() {
    }
    public InvoiceModel(BigInteger invoiceNumber, String companyName, AddressModel address, Boolean isPaid) {
        this.invoiceNumber = invoiceNumber;
        this.companyName = companyName;
        this.hotelAddress = address;
        this.isPaid = isPaid;
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

    public AddressModel getHotelAddress() {
        return hotelAddress;
    }
    public void setHotelAddress(AddressModel hotelAddress) {
        this.hotelAddress = hotelAddress;
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
                "invoiceNumber=" + invoiceNumber +
                "companyName='" + companyName + '\'' +
                ", hotelAddress=" + hotelAddress +
                ", isPaid=" + isPaid +
                '}';
    }
}
