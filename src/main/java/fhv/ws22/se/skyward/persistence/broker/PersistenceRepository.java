package fhv.ws22.se.skyward.persistence.broker;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.UUID;

public interface PersistenceRepository<T> {
    EntityManagerFactory fact = Persistence.createEntityManagerFactory("skyward");
    EntityManager entityManager = fact.createEntityManager();


    List<T> getAll();
    T get(UUID id);
    void add(T t);
    void update(UUID id, T t);
    UUID addAndReturnId(T t);

    default void delete(UUID id, Class<T> clazz) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.getTransaction().commit();
    }
}
