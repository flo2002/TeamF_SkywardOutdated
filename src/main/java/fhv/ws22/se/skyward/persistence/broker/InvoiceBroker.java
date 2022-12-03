package fhv.ws22.se.skyward.persistence.broker;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Address;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.Invoice;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceBroker extends BrokerBase<InvoiceModel> {
    AddressBroker addressBroker;
    BookingBroker bookingBroker;

    public InvoiceBroker(DatabaseFacade dbf, EntityManager em) {
        super(em);
        this.addressBroker = dbf.getAddressBroker();
        this.bookingBroker = dbf.getBookingBroker();
    }

    private void addDependenciesIfNotExists(InvoiceModel invoice) {
        addressBroker.add(invoice.getHotelAddress());
        addressBroker.add(invoice.getCustomerAddress());
    }

    public <S extends AbstractModel> UUID addAndReturnId(S s) {
        InvoiceModel invoice = (InvoiceModel) s;
        addDependenciesIfNotExists(invoice);

        Booking booking = (Booking) entityManager.createQuery("FROM Booking WHERE bookingNumber = :bookingNumber")
                .setParameter("bookingNumber", invoice.getBooking().getBookingNumber())
                .getSingleResult();

        Address hotelAddress = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                .setParameter("street", invoice.getHotelAddress().getStreet())
                .setParameter("number", invoice.getHotelAddress().getHouseNumber())
                .setParameter("zip", invoice.getHotelAddress().getZipCode())
                .setParameter("city", invoice.getHotelAddress().getCity())
                .setParameter("country", invoice.getHotelAddress().getCountry())
                .getSingleResult();

        Address customerAddress = (Address) entityManager.createQuery("FROM Address WHERE street = :street AND houseNumber = :number AND zipCode = :zip AND city = :city AND country = :country")
                .setParameter("street", invoice.getCustomerAddress().getStreet())
                .setParameter("number", invoice.getCustomerAddress().getHouseNumber())
                .setParameter("zip", invoice.getCustomerAddress().getZipCode())
                .setParameter("city", invoice.getCustomerAddress().getCity())
                .setParameter("country", invoice.getCustomerAddress().getCountry())
                .getSingleResult();

        Invoice invoiceEntity = invoice.toEntity();
        invoiceEntity.setBooking(booking);
        invoiceEntity.setHotelAddress(hotelAddress);
        invoiceEntity.setCustomerAddress(customerAddress);

        entityManager.getTransaction().begin();
        entityManager.persist(invoiceEntity);
        entityManager.getTransaction().commit();
        return invoiceEntity.getId();
    }
}
