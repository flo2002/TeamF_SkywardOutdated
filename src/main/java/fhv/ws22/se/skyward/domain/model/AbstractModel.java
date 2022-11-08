package fhv.ws22.se.skyward.domain.model;

import org.modelmapper.ModelMapper;

import java.util.UUID;

public abstract class AbstractModel {
    protected ModelMapper modelMapper;
    private UUID id;

    public AbstractModel() {
        modelMapper = new ModelMapper();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
}
