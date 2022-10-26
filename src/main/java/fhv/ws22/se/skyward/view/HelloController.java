package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.HelloApplication;
import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    DatabaseFacade dbf;
    @FXML
    private Label welcomeText;

    public HelloController() {
        dbf = HelloApplication.getDbf();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(dbf.getAllPersons().get(0).getFirstName());
    }
}