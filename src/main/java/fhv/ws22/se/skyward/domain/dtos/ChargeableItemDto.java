package fhv.ws22.se.skyward.domain.dtos;

import fhv.ws22.se.skyward.domain.model.ChargeableItemModel;
import org.modelmapper.ModelMapper;

public class ChargeableItemDto extends AbstractDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;

    public ChargeableItemDto() {
    }
    public ChargeableItemDto(String name, String description, Double price, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
