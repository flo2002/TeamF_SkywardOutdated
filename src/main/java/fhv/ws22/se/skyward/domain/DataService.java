package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;

import java.util.List;
import java.util.UUID;

public interface DataService {
    <T extends AbstractModel> List<? extends AbstractModel> getAll(Class<T> clazz);
    <T extends AbstractModel> T get(UUID id, Class<T> clazz);
    <T extends AbstractModel> void add(T t);
    <T extends AbstractModel> UUID addAndReturnId(Class<T> clazz, T t);
    <T extends AbstractModel> void update(UUID id, T t);
    <T extends AbstractModel> void delete(UUID id, Class<T> clazz);
}
