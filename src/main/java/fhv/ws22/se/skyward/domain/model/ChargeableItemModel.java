package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.ChargeableItemDto;
import fhv.ws22.se.skyward.persistence.entity.ChargeableItem;
import org.hibernate.cfg.NotYetImplementedException;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;

public class ChargeableItemModel extends AbstractModel {
    private String name;
    private BigInteger price;
    private BookingModel booking;
    private InvoiceModel invoice;

    public ChargeableItemModel() {
    }
    public ChargeableItemModel(String name, BigInteger price, BookingModel booking, InvoiceModel invoice) {
        this.name = name;
        this.price = price;
        this.booking = booking;
        this.invoice = invoice;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getPrice() {
        return price;
    }
    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BookingModel getBooking() {
        return booking;
    }
    public void setBooking(BookingModel booking) {
        this.booking = booking;
    }

    public InvoiceModel getInvoice() {
        return invoice;
    }
    public void setInvoice(InvoiceModel invoice) {
        this.invoice = invoice;
    }

    public ChargeableItemDto toDto() {
        return modelMapper.map(this, ChargeableItemDto.class);
    }
    public static ChargeableItemModel toModel(ChargeableItemDto chargeableItem) {
        ModelMapper mm = new ModelMapper();
        return mm.map(chargeableItem, ChargeableItemModel.class);
    }

    public ChargeableItem toEntity() {
        return modelMapper.map(this, ChargeableItem.class);
    }
    public static ChargeableItemModel toModel(ChargeableItem chargeableItem) {
        ModelMapper mm = new ModelMapper();
        return mm.map(chargeableItem, ChargeableItemModel.class);
    }

    @Override
    public String toString() {
        return "ChargableItemModel{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", booking=" + booking +
                ", invoice=" + invoice +
                '}';
    }
}
