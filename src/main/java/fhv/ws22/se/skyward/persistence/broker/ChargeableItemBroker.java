package fhv.ws22.se.skyward.persistence.broker;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.ChargeableItemModel;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.persistence.entity.AbstractEntity;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.ChargeableItem;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChargeableItemBroker extends BrokerBase<ChargeableItemModel> {
    public ChargeableItemBroker(EntityManager entityManager) {
        super(entityManager);
    }

    public <S extends AbstractModel> UUID addAndReturnId(S s) {
        ChargeableItemModel chargeableItem = (ChargeableItemModel) s;

        Booking booking = (Booking) entityManager.createQuery("FROM Booking WHERE bookingNumber = :bookingNumber")
                .setParameter("bookingNumber", chargeableItem.getBooking().getBookingNumber())
                .getSingleResult();

        ChargeableItem ci = chargeableItem.toEntity();
        ci.setBooking(booking);

        entityManager.getTransaction().begin();
        entityManager.persist(ci);
        entityManager.getTransaction().commit();
        return ci.getId();
    }
}
