package fhv.ws22.se.skyward.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SearchCustomerController {

    private static final Logger logger = LogManager.getLogger("SearchCustomerController");

    @FXML
    public void onConfirmCustomerSearchButtonClick(ActionEvent event) {
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/add-guests.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            logger.error("objects: SearchCustomerController, msg: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
