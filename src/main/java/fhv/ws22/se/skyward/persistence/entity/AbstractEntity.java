package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.math.BigInteger;
import java.util.UUID;

@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Transient
    private static BigInteger bookingNumber;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public static BigInteger getBookingNum() {
        if (bookingNumber == null) {
            bookingNumber = new BigInteger("0");
        }
        bookingNumber = bookingNumber.add(new BigInteger("1"));
        return bookingNumber;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false; // null or other class
        }
        AbstractEntity other = (AbstractEntity) obj;

        if (id != null) {
            return id.equals(other.id);
        }
        return super.equals(other);
    }
}
