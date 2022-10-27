package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.model.Person;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Doe");

        dbf.add(john);
    }
}
