package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    DatabaseFacade dbf;
    @FXML
    private Label welcomeText;

    public HelloController() {
        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Doe");

        this.dbf = new DatabaseFacade();
        dbf.addPerson(john);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(dbf.getAllPersons().get(0).getFirstName());
    }
}