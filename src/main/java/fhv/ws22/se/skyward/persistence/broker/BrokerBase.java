package fhv.ws22.se.skyward.persistence.broker;

import java.util.List;

public abstract class BrokerBase<T> {
    public abstract List<T> getAll();
    public abstract void add(T t);
    public abstract void update(T t);
    public abstract void delete(T t);
}
