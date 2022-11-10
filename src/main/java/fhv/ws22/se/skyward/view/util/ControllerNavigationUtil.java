package fhv.ws22.se.skyward.view.util;

import javafx.event.Event;
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

public class ControllerNavigationUtil {
    private static final Logger logger = LogManager.getLogger("ControllerNavigationUtil");

    public static void navigate(Event event, String path, String stageTitle) {
        try {
            URL url = new File(path).toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(stageTitle);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            logger.error("objects: ControllerNavigationUtil, msg: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
