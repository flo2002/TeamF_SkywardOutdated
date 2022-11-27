package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Room;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.UUID;

public abstract class BrokerBase<T> {
    protected EntityManager entityManager;

    public BrokerBase(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public abstract List<T> getAll();
    public <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        AbstractEntity aE = entityManager.find(entityClazz, id);
        return S.toModel(aE);
    }
    public abstract <S extends AbstractModel> void add(S t);
    public <S extends AbstractModel> void update(UUID id, S s) {
        Room tmpRoom = s.toEntity();
        tmpRoom.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpRoom);
        entityManager.getTransaction().commit();
    }
    public void delete(UUID id, Class<? extends AbstractEntity> clazz) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.getTransaction().commit();
    }
    public abstract <S extends AbstractModel> UUID addAndReturnId(S s);
}
