package fhv.ws22.se.skyward.persistence.broker;

import java.util.List;
import java.util.UUID;

public abstract class BrokerBase<T> implements PersistenceRepository<T> {
    public abstract List<T> getAll();
    public abstract T get(UUID id);
    public abstract void add(T t);
    public abstract void update(UUID id, T t);
    //public abstract void delete(UUID id, Class<T> clazz);
    public abstract UUID addAndReturnId(T t);
}
