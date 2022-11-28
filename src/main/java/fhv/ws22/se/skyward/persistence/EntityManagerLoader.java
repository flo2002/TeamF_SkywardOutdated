package fhv.ws22.se.skyward.persistence;

import com.google.inject.Provider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerLoader implements Provider<EntityManager> {
    @Override
    public EntityManager get() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("skyward");
        return fact.createEntityManager();
    }
}
