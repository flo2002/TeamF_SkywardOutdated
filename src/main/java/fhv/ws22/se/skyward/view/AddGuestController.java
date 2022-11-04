package fhv.ws22.se.skyward.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AddGuestController {

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/create-bookings.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
//            stage.setTitle("Home");
//            stage.setScene(new Scene(parent));
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSearchCustomerButtonClick(ActionEvent event) {
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/search-customer.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            //Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddAnotherCustomerButtonClick(ActionEvent event) {

    }
}
