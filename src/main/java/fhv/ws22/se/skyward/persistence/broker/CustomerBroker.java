package fhv.ws22.se.skyward.persistence.broker;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.AddressModel;
import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.domain.model.InvoiceModel;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Address;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.Customer;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerBroker extends BrokerBase<CustomerModel> {
    private final DatabaseFacade dbf;

    public CustomerBroker(DatabaseFacade databaseFacade, EntityManager em) {
        super(em);
        dbf = databaseFacade;
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

    public <S extends AbstractModel> S get(UUID id, Class<? extends AbstractEntity> entityClazz) {
        return super.get(id, entityClazz);
    }

    private void addDependenciesIfNotExists(CustomerModel customer) {
        dbf.add(customer.getAddress());
    }

    public <S extends AbstractModel> UUID addAndReturnId(S s) {
        CustomerModel customer = (CustomerModel) s;
        addDependenciesIfNotExists(customer);

        Address address = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                .setParameter("street", customer.getAddress().getStreet())
                .setParameter("number", customer.getAddress().getHouseNumber())
                .setParameter("zip", customer.getAddress().getZipCode())
                .setParameter("city", customer.getAddress().getCity())
                .setParameter("country", customer.getAddress().getCountry())
                .getSingleResult();

        Customer customerEntity = customer.toEntity();
        customerEntity.setBillingAddress(address);

        if (entityManager.createQuery("FROM Customer WHERE firstName = :firstName AND lastName = :lastName AND billingAddress = :address")
                .setParameter("firstName", customerEntity.getFirstName())
                .setParameter("lastName", customerEntity.getLastName())
                .setParameter("address", customerEntity.getBillingAddress())
                .getResultList().isEmpty()) {
            entityManager.getTransaction().begin();
            entityManager.persist(customerEntity);
            entityManager.getTransaction().commit();
            return customerEntity.getId();
        }
        return null;
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
