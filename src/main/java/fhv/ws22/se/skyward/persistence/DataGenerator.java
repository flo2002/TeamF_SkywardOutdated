package fhv.ws22.se.skyward.persistence;

import fhv.ws22.se.skyward.view.model.PersonView;

public class DataGenerator {
    public static void generateData() {
        DatabaseFacade dbf = DatabaseFacade.getInstance();

        PersonView john = new PersonView();
        john.setFirstName("John");
        john.setLastName("Doe");

        dbf.add(john);
    }
}
