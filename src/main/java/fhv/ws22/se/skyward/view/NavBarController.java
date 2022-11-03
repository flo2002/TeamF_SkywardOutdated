package fhv.ws22.se.skyward.view;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.boot.archive.internal.UrlInputStreamAccess;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class NavBarController {
    public NavBarController() {

    }
    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        System.out.println("This works, right?");
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/home.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();

            NotificationController.getInstance().showSuccessNotification("Home", stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onBookingButtonClick(ActionEvent event) {
        System.out.println("To Bookings we go!");
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/bookings.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Bookings");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}