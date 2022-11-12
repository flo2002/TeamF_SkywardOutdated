package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.persistence.entity.Address;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.Customer;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerBroker extends BrokerBase<CustomerModel> {
    private final EntityManager entityManager;

    public CustomerBroker(EntityManager em) {
        entityManager = em;
    }

    @SuppressWarnings("unchecked")
    public List<CustomerModel> getAll() {
        List<Customer> customers = entityManager.createQuery("FROM Customer").getResultList();

        List<CustomerModel> customerModels = new ArrayList<CustomerModel>();
        for (Customer p : customers) {
            customerModels.add(CustomerModel.toModel(p));
        }

        return customerModels;
    }

    public CustomerModel get(UUID id) {
        Customer customer = entityManager.find(Customer.class, id);
        return CustomerModel.toModel(customer);
    }

    public void add(CustomerModel customer) {
        Address address = null;

        entityManager.getTransaction().begin();
        if (entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                .setParameter("street", customer.getAddress().getStreet())
                .setParameter("number", customer.getAddress().getHouseNumber())
                .setParameter("zip", customer.getAddress().getZipCode())
                .setParameter("city", customer.getAddress().getCity())
                .setParameter("country", customer.getAddress().getCountry())
                .getResultList().isEmpty()) {
            address = new Address();
            address.setStreet(customer.getAddress().getStreet());
            address.setHouseNumber(customer.getAddress().getHouseNumber());
            address.setZipCode(customer.getAddress().getZipCode());
            address.setCity(customer.getAddress().getCity());
            address.setCountry(customer.getAddress().getCountry());
            entityManager.persist(address);
            entityManager.flush();
        }

        if (address == null) {
            address = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                    .setParameter("street", customer.getAddress().getStreet())
                    .setParameter("number", customer.getAddress().getHouseNumber())
                    .setParameter("zip", customer.getAddress().getZipCode())
                    .setParameter("city", customer.getAddress().getCity())
                    .setParameter("country", customer.getAddress().getCountry())
                    .getSingleResult();
        }

        Customer customerEntity = customer.toEntity();
        customerEntity.setBillingAddress(address);

        entityManager.persist(customerEntity);
        entityManager.getTransaction().commit();
    }


    public void update(UUID id, CustomerModel customer) {
        Customer tmpCustomer = customer.toEntity();
        tmpCustomer.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpCustomer);
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Customer.class, id));
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(CustomerModel customerModel) {
        Customer tmpCustomer = customerModel.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(tmpCustomer);
        entityManager.getTransaction().commit();
        return tmpCustomer.getId();
    }
}
