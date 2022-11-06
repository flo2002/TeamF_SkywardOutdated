package fhv.ws22.se.skyward;

import fhv.ws22.se.skyward.persistence.DataGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MainApplication extends Application {
    private static final Logger logger = LogManager.getLogger("HelloApplication");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("homescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 674,465);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        //hello

    }

    public static void main(String[] args) {
        //DataGenerator.generateData();

        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");

        launch();
    }
}