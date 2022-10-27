package fhv.ws22.se.skyward.view.controller;

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
        welcomeText.setText(dbf.getAllPersonViews().get(0).getFirstName());
    }
}