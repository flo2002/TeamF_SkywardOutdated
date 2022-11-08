package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;

import java.util.List;
import java.util.UUID;

public class Session {
    private final DatabaseFacade dbf;

    public Session() {
        dbf = DatabaseFacade.getInstance();
    }


    public <T extends AbstractDto> List getAll(Class<T> clazz) {
        return dbf.getAll(clazz);
    }
    public <T extends AbstractDto> void add(T t) {
        dbf.add(t);
    }
    public <T extends AbstractDto> void update(UUID id, T t) {
        dbf.update(id, t);
    };
    public <T extends AbstractDto> void delete(UUID id, Class<T> clazz) {
        dbf.delete(id, clazz);
    };
}
