package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.DTOs.PersonDto;
import fhv.ws22.se.skyward.model.DTOs.RoomDto;
import fhv.ws22.se.skyward.model.DTOs.RoomStateDto;
import fhv.ws22.se.skyward.model.DTOs.RoomTypeDto;

import java.math.BigDecimal;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        RoomTypeDto rt = new RoomTypeDto("Single", new BigDecimal(100));
        RoomTypeDto rt2 = new RoomTypeDto("Double", new BigDecimal(200));

        RoomStateDto rs = new RoomStateDto("free");
        RoomStateDto rs2 = new RoomStateDto("free_for_cleaning");
        RoomStateDto rs3 = new RoomStateDto("occupied");
        RoomStateDto rs4 = new RoomStateDto("out_of_order");

        RoomDto r = new RoomDto(101, rt, rs);
        RoomDto r2 = new RoomDto(201, rt, rs2);
        RoomDto r3 = new RoomDto(301, rt2, rs3);

        PersonDto john = new PersonDto("John", "Doe");
        PersonDto jane = new PersonDto("Jane", "Doe");

        dbf.add(rt);
        dbf.add(rt2);
        dbf.add(rs);
        dbf.add(rs2);
        dbf.add(rs3);
        dbf.add(rs4);
        dbf.add(r);
        dbf.add(r2);
        dbf.add(r3);
        dbf.add(john);
        dbf.add(jane);
    }
}
