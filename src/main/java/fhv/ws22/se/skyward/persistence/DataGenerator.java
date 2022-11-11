package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        RoomModel r = new RoomModel(101, "Single", new BigDecimal(100), "FREE");
        RoomModel r1 = new RoomModel(102, "Single", new BigDecimal(100), "FREE");
        RoomModel r2 = new RoomModel(201, "Double", new BigDecimal(200), "FREE");
        RoomModel r3 = new RoomModel(202, "Double", new BigDecimal(200), "FREE");
        RoomModel r4 = new RoomModel(301, "Double", new BigDecimal(300), "FREE");
        RoomModel r5 = new RoomModel(303, "Triple", new BigDecimal(300), "FREE");

        CustomerModel john = new CustomerModel("John", "Doe");
        CustomerModel jane = new CustomerModel("Jane", "Doe");

        BookingModel b = new BookingModel(LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, List.of(john, jane), List.of(r2));
        BookingModel b2 = new BookingModel(LocalDateTime.now().plusWeeks(2), LocalDateTime.now().plusWeeks(3), true, List.of(john), List.of(r3));

        dbf.add(john);
        dbf.add(jane);
        dbf.add(r);
        dbf.add(r1);
        dbf.add(r2);
        dbf.add(r3);
        dbf.add(r4);
        dbf.add(r5);
        dbf.add(b);
        dbf.add(b2);
    }
}
