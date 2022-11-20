package fhv.ws22.se.skyward.persistence.broker;

import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.ChargeableItemModel;
import fhv.ws22.se.skyward.persistence.entity.Booking;
import fhv.ws22.se.skyward.persistence.entity.ChargeableItem;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChargeableItemBroker extends BrokerBase<ChargeableItemModel> {
    private final EntityManager entityManager;

    public ChargeableItemBroker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<ChargeableItemModel> getAll() {
        List<ChargeableItem> chargeableItems = entityManager.createQuery("FROM ChargeableItem").getResultList();

        List<ChargeableItemModel> chargeableItemModels = new ArrayList<ChargeableItemModel>();
        for (ChargeableItem c : chargeableItems) {
            chargeableItemModels.add(ChargeableItemModel.toModel(c));
        }

        return chargeableItemModels;
    }

    public ChargeableItemModel get(UUID id) {
        ChargeableItem chargeableItem = entityManager.find(ChargeableItem.class, id);
        return ChargeableItemModel.toModel(chargeableItem);
    }

    public void add(ChargeableItemModel chargeableItem) {
        entityManager.getTransaction().begin();
        Booking booking = (Booking) entityManager.createQuery("FROM Booking WHERE bookingNumber = :bookingNumber")
                .setParameter("bookingNumber", chargeableItem.getBooking().getBookingNumber())
                .getSingleResult();

        ChargeableItem c = chargeableItem.toEntity();
        c.setBooking(booking);

        entityManager.persist(c);
        entityManager.getTransaction().commit();
    }

    public void update(UUID id, ChargeableItemModel chargeableItem) {
        ChargeableItem tmpChargeableItem = chargeableItem.toEntity();
        tmpChargeableItem.setId(id);
        entityManager.getTransaction().begin();
        entityManager.merge(tmpChargeableItem);
        entityManager.getTransaction().commit();
    }

    public void delete(UUID id) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(ChargeableItem.class, id));
        entityManager.getTransaction().commit();
    }

    public UUID addAndReturnId(ChargeableItemModel chargeableItem) {
        Booking booking = (Booking) entityManager.createQuery("FROM Booking WHERE bookingNumber = :bookingNumber")
                .setParameter("bookingNumber", chargeableItem.getBooking().getBookingNumber())
                .getSingleResult();

        ChargeableItem c = chargeableItem.toEntity();
        c.setBooking(booking);

        entityManager.persist(c);
        entityManager.getTransaction().commit();
        return c.getId();
    }
}
