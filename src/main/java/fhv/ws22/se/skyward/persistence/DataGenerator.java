package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.DTOs.PersonDTO;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        PersonDTO john = new PersonDTO("John", "Doe");
        dbf.add(john);
        dbf.add(john);
    }
}
