package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.model.DTOs.CustomerDto;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    DatabaseFacade dbf;
    @FXML
    private Label welcomeText;

    public HelloController() {
        dbf = DatabaseFacade.getInstance();
    }

    @FXML
    protected void onHelloButtonClick() {
        CustomerDto person = new CustomerDto("Max", "Mustermann");
        dbf.add(person);

        welcomeText.setText(dbf.getPersonByNames(person.getFirstName(), person.getLastName()).getFirstName());
    }
}