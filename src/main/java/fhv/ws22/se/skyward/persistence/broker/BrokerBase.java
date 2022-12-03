package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.Room;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BrokerBase<T> {
    protected EntityManager entityManager;

    public BrokerBase(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public <S extends AbstractModel> List<? extends AbstractModel> getAll(Class<? extends AbstractEntity> entityClazz) {
        List<AbstractEntity> entities = entityManager.createQuery("FROM " + entityClazz.getSimpleName()).getResultList();

        List<AbstractModel> models = new ArrayList<>();
        for (AbstractEntity entity : entities) {
            models.add(AbstractModel.toModel(entity));
        }

        return models;
    }
    public abstract <S extends AbstractModel> UUID addAndReturnId(S s);

    public <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        AbstractEntity aE = entityManager.find(entityClazz, id);
        return S.toModel(aE);
    }
    public <S extends AbstractModel> void add(S s) {
        addAndReturnId(s);
    }
    public <S extends AbstractModel> void update(UUID id, S s) {
        AbstractEntity entity = s.toEntity();
        entity.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }
    public void delete(UUID id, Class<? extends AbstractEntity> clazz) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(clazz, id));
        entityManager.getTransaction().commit();
    }
}
