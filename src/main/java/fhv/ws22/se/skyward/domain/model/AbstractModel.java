package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractModel {
    protected ModelMapper modelMapper;
    private UUID id;

    public AbstractModel() {
        modelMapper = new ModelMapper();
    }

    public abstract <T extends AbstractDto> T toDto();

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

}
