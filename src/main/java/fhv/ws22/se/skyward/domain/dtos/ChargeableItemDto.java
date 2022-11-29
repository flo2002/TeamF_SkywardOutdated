package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.ChargeableItemModel;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class ChargeableItemDto extends AbstractDto {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private BookingDto booking;

    public ChargeableItemDto() {
    }
    public ChargeableItemDto(String name, BigDecimal price, Integer quantity, BookingDto booking) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.booking = booking;
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

    public BookingDto getBooking() {
        return booking;
    }
    public void setBooking(BookingDto booking) {
        this.booking = booking;
    }

    public ChargeableItemModel toModel() {
        return modelMapper.map(this, ChargeableItemModel.class);
    }
    public static ChargeableItemDto toDto(ChargeableItemModel item) {
        ModelMapper mm = new ModelMapper();
        return mm.map(item, ChargeableItemDto.class);
    }

    public String toString() {
        return "ChargeableItemDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", booking=" + booking +
                '}';
    }
}
