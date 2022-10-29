package fhv.ws22.se.skyward.model.DTOs;

import fhv.ws22.se.skyward.model.RoomType;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

public class RoomTypeDto extends AbstractDto {
    private String name;
    private BigDecimal price;

    public RoomTypeDto() {
    }
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

    public RoomType toEntity() {
        return modelMapper.map(this, RoomType.class);
    }
    public static RoomTypeDto toDto(RoomType roomType) {
        ModelMapper mm = new ModelMapper();
        return mm.map(roomType, RoomTypeDto.class);
    }
}
