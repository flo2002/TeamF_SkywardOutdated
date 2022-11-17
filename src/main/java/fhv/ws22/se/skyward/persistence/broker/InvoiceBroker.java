package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.AddressModel;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.InvoiceModel;
import fhv.ws22.se.skyward.persistence.entity.Address;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.Invoice;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceBroker extends BrokerBase<InvoiceModel> {
    public final EntityManager entityManager;

    public InvoiceBroker(EntityManager em) {
        entityManager = em;
    }

    @SuppressWarnings("unchecked")
    public List<InvoiceModel> getAll() {
        List<Invoice> invoices = entityManager.createQuery("FROM Invoice").getResultList();

        List<InvoiceModel> invoiceModels = new ArrayList<InvoiceModel>();
        for (Invoice i : invoices) {
            invoiceModels.add(InvoiceModel.toModel(i));
        }

        return invoiceModels;
    }

    public InvoiceModel get(UUID id) {
        Invoice invoice = entityManager.find(Invoice.class, id);
        return InvoiceModel.toModel(invoice);
    }

    public void add(InvoiceModel invoice) {
        Invoice i = invoice.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(i);
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, InvoiceModel invoice) {
        Invoice tmpInvoice = invoice.toEntity();
        tmpInvoice.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpInvoice);
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Invoice.class, id));
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(InvoiceModel invoice) {
        Invoice tmpInvoice = invoice.toEntity();
        entityManager.getTransaction().begin();
        entityManager.persist(tmpInvoice);
        entityManager.getTransaction().commit();
        return tmpInvoice.getId();
    }
}
