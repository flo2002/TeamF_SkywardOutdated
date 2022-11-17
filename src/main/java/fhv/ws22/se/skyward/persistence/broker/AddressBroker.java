package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AddressModel;
import fhv.ws22.se.skyward.persistence.entity.Address;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddressBroker extends BrokerBase<AddressModel> {
    private final EntityManager entityManager;
    public AddressBroker(EntityManager em) {
        entityManager = em;
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

    public AddressModel get(UUID id) {
        Address address = entityManager.find(Address.class, id);
        return AddressModel.toModel(address);
    }

    public void add(AddressModel address) {
        if (entityManager.createQuery("FROM Address a WHERE a.street = :street AND a.houseNumber = :houseNumber AND a.zipCode = :postalCode AND a.city = :city AND a.country = :country")
                .setParameter("street", address.getStreet())
                .setParameter("houseNumber", address.getHouseNumber())
                .setParameter("postalCode", address.getZipCode())
                .setParameter("city", address.getCity())
                .setParameter("country", address.getCountry())
                .getResultList().isEmpty()) {
            Address a = new Address();
            a.setStreet(address.getStreet());
            a.setHouseNumber(address.getHouseNumber());
            a.setZipCode(address.getZipCode());
            a.setCity(address.getCity());
            a.setCountry(address.getCountry());
            entityManager.getTransaction().begin();
            entityManager.persist(a);
            entityManager.getTransaction().commit();
        }
    }

    public void update(UUID id, AddressModel address) {
        Address tmpAddress = address.toEntity();
        tmpAddress.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpAddress);
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Address.class, id));
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(AddressModel address) {
        Address tmpAddress = address.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(tmpAddress);
        entityManager.getTransaction().commit();
        return tmpAddress.getId();
    }
}