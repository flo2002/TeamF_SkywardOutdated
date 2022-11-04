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

public class CreateBookingController {
    public CreateBookingController() {

    }

    @FXML
    public void onCreateBookingButtonClick() {

    }

    @FXML
    public void onReturnToBookingsButtonClick(ActionEvent event) {
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/bookings.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddRoomButtonClick(ActionEvent event) {
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/add-rooms.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddGuestButtonClick(ActionEvent event) {
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/add-guests.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
