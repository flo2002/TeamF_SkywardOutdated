package fhv.ws22.se.skyward.model.DTOs;

import org.modelmapper.ModelMapper;

public abstract class AbstractDto {
    protected ModelMapper modelMapper;

    public AbstractDto() {
        modelMapper = new ModelMapper();
    }
}
