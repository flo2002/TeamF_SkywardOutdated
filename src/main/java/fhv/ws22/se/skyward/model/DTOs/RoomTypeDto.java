package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.RoomType;

import java.math.BigDecimal;

public class RoomTypeDto extends DTOMarker {
    private String name;
    private BigDecimal price;

    public RoomTypeDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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

    public RoomType toRoomType() {
        RoomType roomType = new RoomType();
        roomType.setName(name);
        roomType.setPrice(price);
        return roomType;
    }
}
