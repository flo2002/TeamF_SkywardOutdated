package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.model.Customer;
import fhv.ws22.se.skyward.model.DTOs.CustomerDto;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class PersonBroker extends BrokerBase<CustomerDto> {
    private final EntityManager entityManager;

    public PersonBroker(EntityManager em) {
        entityManager = em;
    }

    @SuppressWarnings("unchecked")
    public List<CustomerDto> getAll() {
        List<Customer> customers = (List<Customer>) entityManager.createQuery("FROM Customer").getResultList();

        List<CustomerDto> customerDtos = new ArrayList<CustomerDto>();
        for (Customer p : customers) {
            customerDtos.add(CustomerDto.toDto(p));
        }

        return customerDtos;
    }

    public CustomerDto getPersonByNames(String firstName, String lastName) {
        Customer p = (Customer) entityManager.createQuery("FROM Customer WHERE firstName = :firstName AND lastName = :lastName")
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getSingleResult();
        return CustomerDto.toDto(p);
    }

    public void add(CustomerDto person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person.toEntity());
        entityManager.getTransaction().commit();
    }

    public void update(CustomerDto person) {
        entityManager.getTransaction().begin();
        entityManager.merge(person.toEntity());
        entityManager.getTransaction().commit();
    }

    public void delete(CustomerDto person) {
        entityManager.getTransaction().begin();
        entityManager.remove(person.toEntity());
        entityManager.getTransaction().commit();
    }
}
