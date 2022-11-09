package fhv.ws22.se.skyward.persistence.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name"}))
public class Customer extends AbstractEntity {
    private String firstName;
    private String lastName;
    @ManyToMany(mappedBy = "customers")
    private List<Booking> bookings;


    public Customer() {
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bookings=" + bookings +
                '}';
    }
}
