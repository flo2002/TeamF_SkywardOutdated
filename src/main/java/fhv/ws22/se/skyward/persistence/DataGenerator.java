package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.entity.Address;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        RoomModel r = new RoomModel(101, "Single", new BigDecimal(100), "cleaned");
        RoomModel r1 = new RoomModel(102, "Single", new BigDecimal(100), "cleaned");
        RoomModel r2 = new RoomModel(201, "Double", new BigDecimal(200), "cleaned");
        RoomModel r3 = new RoomModel(202, "Double", new BigDecimal(200), "cleaned");
        RoomModel r4 = new RoomModel(301, "Double", new BigDecimal(300), "cleaned");
        RoomModel r5 = new RoomModel(303, "Triple", new BigDecimal(300), "cleaned");

        AddressModel customerAddress = new AddressModel("MainStreet", "43", "1234", "Vienna", "Austria");
        AddressModel hotelAddress = new AddressModel("ExampleStreet", "2", "1234", "New York", "United States");

        CustomerModel john = new CustomerModel("John", "Doe", customerAddress);
        CustomerModel jane = new CustomerModel("Jane", "Doe", customerAddress);

        BookingModel b = new BookingModel(LocalDateTime.now(), LocalDateTime.now().plusDays(1), false, List.of(john, jane), List.of(r2));
        BookingModel b2 = new BookingModel(LocalDateTime.now().plusWeeks(2), LocalDateTime.now().plusWeeks(3), true, List.of(john), List.of(r3));

        InvoiceModel i = new InvoiceModel("Skyward International", hotelAddress, b, false);

        dbf.add(r);
        dbf.add(r1);
        dbf.add(r2);
        dbf.add(r3);
        dbf.add(r4);
        dbf.add(r5);
        dbf.add(customerAddress);
        dbf.add(hotelAddress);
        dbf.add(john);
        dbf.add(jane);
        dbf.add(b);
        dbf.add(b2);
        //dbf.add(i);
    }
}
