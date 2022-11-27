package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.persistence.EntityManagerLoader;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public interface PersistenceRepository {
    EntityManager entityManager = new EntityManagerLoader().get();


    <T extends AbstractModel> List<T> getAll();
    default <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        AbstractEntity aE = entityManager.find(entityClazz, id);
        return S.toModel(aE);
    }
    <T extends AbstractModel> void add(T t);
    <T extends AbstractModel> void update(UUID id, T t);
    <T extends AbstractModel> UUID addAndReturnId(T t);

    default void delete(UUID id, Class<? extends AbstractEntity> clazz) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.getTransaction().commit();
    }
}
