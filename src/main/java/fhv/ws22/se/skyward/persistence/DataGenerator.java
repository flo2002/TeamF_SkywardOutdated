package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.entity.Address;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        RoomModel r0 = new RoomModel(101, "Single", "cleaned");
        RoomModel r1 = new RoomModel(102, "Single", "cleaned");
        RoomModel r2 = new RoomModel(201, "Double", "cleaned");
        RoomModel r3 = new RoomModel(202, "Double", "cleaned");
        RoomModel r4 = new RoomModel(301, "Double", "cleaned");
        RoomModel r5 = new RoomModel(303, "Triple", "cleaned");

        AddressModel customerAddress = new AddressModel("MainStreet", "43", "1234", "Vienna", "Austria");

        CustomerModel john = new CustomerModel("John", "Doe", customerAddress);
        CustomerModel jane = new CustomerModel("Jane", "Doe", customerAddress);

        dbf.add(r0);
        dbf.add(r1);
        dbf.add(r2);
        dbf.add(r3);
        dbf.add(r4);
        dbf.add(r5);
        dbf.add(customerAddress);
        dbf.add(john);
        dbf.add(jane);

        try {
            BookingModel b = new BookingModel(LocalDateTime.now(), LocalDateTime.now().plusDays(1), true, List.of(john, jane), List.of(r2));
            BookingModel b2 = new BookingModel(LocalDateTime.now().plusWeeks(2), LocalDateTime.now().plusWeeks(3), false, List.of(john), List.of(r1));
            BookingModel b3 = new BookingModel(LocalDateTime.now().plusWeeks(4), LocalDateTime.now().plusWeeks(5), false, List.of(jane), List.of(r4, r5));
            dbf.add(b);
            dbf.add(b2);
            dbf.add(b3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
