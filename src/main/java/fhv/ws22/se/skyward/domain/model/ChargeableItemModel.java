package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.ChargeableItemDto;
import fhv.ws22.se.skyward.persistence.entity.ChargeableItem;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class ChargeableItemModel extends AbstractModel {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private BookingModel booking;

    public ChargeableItemModel() {
    }
    public ChargeableItemModel(String name, BigDecimal price, Integer quantity, BookingModel booking) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setBooking(booking);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BookingModel getBooking() {
        return booking;
    }
    public void setBooking(BookingModel booking) {
        this.booking = booking;
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
                ", quantity=" + quantity +
                ", booking=" + booking +
                '}';
    }
}
