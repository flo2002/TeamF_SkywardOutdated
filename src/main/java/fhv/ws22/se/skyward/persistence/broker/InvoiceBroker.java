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
        Address hotelAddress = null;
        Address customerAddress = null;

        entityManager.getTransaction().begin();
        if (entityManager.createQuery("FROM Address WHERE street = :street AND number = :number AND zip = :zip AND city = :city")
                .setParameter("street", invoice.getHotelAddress().getStreet())
                .setParameter("number", invoice.getHotelAddress().getHouseNumber())
                .setParameter("zip", invoice.getHotelAddress().getZipCode())
                .setParameter("city", invoice.getHotelAddress().getCity())
                .getResultList().isEmpty()) {
            hotelAddress = new Address();
            hotelAddress.setStreet(invoice.getHotelAddress().getStreet());
            hotelAddress.setHouseNumber(invoice.getHotelAddress().getHouseNumber());
            hotelAddress.setZipCode(invoice.getHotelAddress().getZipCode());
            hotelAddress.setCity(invoice.getHotelAddress().getCity());
            entityManager.persist(hotelAddress);
            entityManager.flush();
        }
        if (entityManager.createQuery("FROM Address WHERE street = :street AND number = :number AND zip = :zip AND city = :city")
                .setParameter("street", invoice.getCustomerAddress().getStreet())
                .setParameter("number", invoice.getCustomerAddress().getHouseNumber())
                .setParameter("zip", invoice.getCustomerAddress().getZipCode())
                .setParameter("city", invoice.getCustomerAddress().getCity())
                .getResultList().isEmpty()) {
            customerAddress = new Address();
            customerAddress.setStreet(invoice.getCustomerAddress().getStreet());
            customerAddress.setHouseNumber(invoice.getCustomerAddress().getHouseNumber());
            customerAddress.setZipCode(invoice.getCustomerAddress().getZipCode());
            customerAddress.setCity(invoice.getCustomerAddress().getCity());
            entityManager.persist(customerAddress);
            entityManager.flush();
        }

        Booking booking = (Booking) entityManager.createQuery("FROM Booking WHERE bookingNumber = :bookingNumber")
            .setParameter("bookingNumber", invoice.getBooking().getBookingNumber())
            .getSingleResult();

        if (hotelAddress == null) {
            hotelAddress = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND number = :number AND zip = :zip AND city = :city")
                    .setParameter("street", invoice.getHotelAddress().getStreet())
                    .setParameter("number", invoice.getHotelAddress().getHouseNumber())
                    .setParameter("zip", invoice.getHotelAddress().getZipCode())
                    .setParameter("city", invoice.getHotelAddress().getCity())
                    .getSingleResult();
        }
        if (customerAddress == null) {
            customerAddress = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND number = :number AND zip = :zip AND city = :city")
                    .setParameter("street", invoice.getCustomerAddress().getStreet())
                    .setParameter("number", invoice.getCustomerAddress().getHouseNumber())
                    .setParameter("zip", invoice.getCustomerAddress().getZipCode())
                    .setParameter("city", invoice.getCustomerAddress().getCity())
                    .getSingleResult();
        }

        Invoice i = invoice.toEntity();
        i.setBooking(booking);
        i.setHotelAddress(hotelAddress);
        i.setCustomerAddress(customerAddress);

        entityManager.persist(i);
        entityManager.flush();
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
        Address hotelAddress = null;
        Address customerAddress = null;

        entityManager.getTransaction().begin();
        if (entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                .setParameter("street", invoice.getHotelAddress().getStreet())
                .setParameter("number", invoice.getHotelAddress().getHouseNumber())
                .setParameter("zip", invoice.getHotelAddress().getZipCode())
                .setParameter("city", invoice.getHotelAddress().getCity())
                .setParameter("country", invoice.getHotelAddress().getCountry())
                .getResultList().isEmpty()) {
            hotelAddress = new Address();
            hotelAddress.setStreet(invoice.getHotelAddress().getStreet());
            hotelAddress.setHouseNumber(invoice.getHotelAddress().getHouseNumber());
            hotelAddress.setZipCode(invoice.getHotelAddress().getZipCode());
            hotelAddress.setCity(invoice.getHotelAddress().getCity());
            hotelAddress.setCountry(invoice.getHotelAddress().getCountry());
            entityManager.persist(hotelAddress);
            entityManager.flush();
        }
        if (entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                .setParameter("street", invoice.getCustomerAddress().getStreet())
                .setParameter("number", invoice.getCustomerAddress().getHouseNumber())
                .setParameter("zip", invoice.getCustomerAddress().getZipCode())
                .setParameter("city", invoice.getCustomerAddress().getCity())
                .setParameter("country", invoice.getCustomerAddress().getCountry())
                .getResultList().isEmpty()) {
            customerAddress = new Address();
            customerAddress.setStreet(invoice.getCustomerAddress().getStreet());
            customerAddress.setHouseNumber(invoice.getCustomerAddress().getHouseNumber());
            customerAddress.setZipCode(invoice.getCustomerAddress().getZipCode());
            customerAddress.setCity(invoice.getCustomerAddress().getCity());
            customerAddress.setCountry(invoice.getCustomerAddress().getCountry());
            entityManager.persist(customerAddress);
            entityManager.flush();
        }

        Booking booking = (Booking) entityManager.createQuery("FROM Booking WHERE bookingNumber = :bookingNumber")
                .setParameter("bookingNumber", invoice.getBooking().getBookingNumber())
                .getSingleResult();

        if (hotelAddress == null) {
            hotelAddress = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                    .setParameter("street", invoice.getHotelAddress().getStreet())
                    .setParameter("number", invoice.getHotelAddress().getHouseNumber())
                    .setParameter("zip", invoice.getHotelAddress().getZipCode())
                    .setParameter("city", invoice.getHotelAddress().getCity())
                    .setParameter("country", invoice.getHotelAddress().getCountry())
                    .getSingleResult();
        }
        if (customerAddress == null) {
            customerAddress = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                    .setParameter("street", invoice.getCustomerAddress().getStreet())
                    .setParameter("number", invoice.getCustomerAddress().getHouseNumber())
                    .setParameter("zip", invoice.getCustomerAddress().getZipCode())
                    .setParameter("city", invoice.getCustomerAddress().getCity())
                    .setParameter("country", invoice.getCustomerAddress().getCountry())
                    .getSingleResult();
        }

        Invoice i = invoice.toEntity();
        i.setBooking(booking);
        i.setHotelAddress(hotelAddress);
        i.setCustomerAddress(customerAddress);

        entityManager.persist(i);
        entityManager.getTransaction().commit();
        return i.getId();
    }
}
