package fhv.ws22.se.skyward.persistence.broker;

import java.util.List;

public abstract class BrokerBase<T> {
    protected abstract List<T> getAll();
    protected abstract void add(T t);
    protected abstract void update(T t);
    protected abstract void delete(T t);
}
