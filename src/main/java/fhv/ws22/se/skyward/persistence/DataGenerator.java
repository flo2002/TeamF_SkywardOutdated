package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.DTOs.BookingDto;
import fhv.ws22.se.skyward.model.DTOs.PersonDto;
import fhv.ws22.se.skyward.model.DTOs.RoomDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        RoomDto r = new RoomDto(101, "Single", new BigDecimal(100), "free");
        RoomDto r2 = new RoomDto(201, "Double", new BigDecimal(200), "free");
        RoomDto r3 = new RoomDto(301, "Double", new BigDecimal(300), "free");

        PersonDto john = new PersonDto("John", "Doe");
        PersonDto jane = new PersonDto("Jane", "Doe");

        BookingDto b = new BookingDto(LocalDateTime.now(), LocalDateTime.now().plusDays(1), List.of(john, jane), List.of(r2));
        BookingDto b2 = new BookingDto(LocalDateTime.now().plusWeeks(2), LocalDateTime.now().plusWeeks(3), List.of(john), List.of(r3));

        dbf.add(john);
        dbf.add(jane);
        dbf.add(r);
        dbf.add(r2);
        dbf.add(r3);
        dbf.add(b);
        dbf.add(b2);
    }
}
