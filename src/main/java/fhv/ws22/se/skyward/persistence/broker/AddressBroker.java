package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.AddressModel;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Address;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressBroker extends BrokerBase<AddressModel> {
    public AddressBroker(EntityManager entityManager) {
        super(entityManager);
    }

    @SuppressWarnings("unchecked")
    public List<AddressModel> getAll() {
        List<Address> addresses = entityManager.createQuery("FROM Address a").getResultList();

        List<AddressModel> addressModels = new ArrayList<AddressModel>();
        for (Address a : addresses) {
            addressModels.add(AddressModel.toModel(a));
        }

        return addressModels;
    }

    public <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        return super.get(id, entityClazz);
    }

    public <S extends AbstractModel> UUID addAndReturnId(S s) {
        AddressModel address = (AddressModel) s;
        Address addressEntity = address.toEntity();

        if (entityManager.createQuery("FROM Address a WHERE a.street = :street AND a.houseNumber = :houseNumber AND a.zipCode = :postalCode AND a.city = :city AND a.country = :country")
                .setParameter("street", addressEntity.getStreet())
                .setParameter("houseNumber", addressEntity.getHouseNumber())
                .setParameter("postalCode", addressEntity.getZipCode())
                .setParameter("city", addressEntity.getCity())
                .setParameter("country", addressEntity.getCountry())
                .getSingleResult() == null) {
            addressEntity.setStreet(addressEntity.getStreet());
            addressEntity.setHouseNumber(addressEntity.getHouseNumber());
            addressEntity.setZipCode(addressEntity.getZipCode());
            addressEntity.setCity(addressEntity.getCity());
            addressEntity.setCountry(addressEntity.getCountry());
            entityManager.getTransaction().begin();
            entityManager.persist(addressEntity);
            entityManager.getTransaction().commit();
        }

        return addressEntity.getId();
    }

    public <S extends AbstractModel> void add(S s) {
        addAndReturnId(s);
    }

    public <S extends AbstractModel> void update(UUID id, S s) {
        super.update(id, s);
    }

    public void delete(UUID id, Class<? extends AbstractEntity> clazz) {
        super.delete(id, clazz);
    }
}
