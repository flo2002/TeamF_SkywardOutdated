package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.CustomerModel;
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
        List<Customer> customers = (List<Customer>) entityManager.createQuery("FROM Customer").getResultList();

        List<CustomerModel> customerModels = new ArrayList<CustomerModel>();
        for (Customer p : customers) {
            customerModels.add(CustomerModel.toModel(p));
        }

        return customerModels;
    }

    public CustomerModel getCustomerByNames(String firstName, String lastName) {
        Customer p = (Customer) entityManager.createQuery("FROM Customer WHERE firstName = :firstName AND lastName = :lastName")
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();
        return CustomerModel.toModel(p);
    }

    public CustomerModel get(UUID id) {
        Customer customer = entityManager.find(Customer.class, id);
        return CustomerModel.toModel(customer);
    }

    public void add(CustomerModel customer) {
        entityManager.getTransaction().begin();
        entityManager.persist(customer.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, CustomerModel customer) {
        entityManager.getTransaction().begin();
        entityManager.merge(customer.toEntity());
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
