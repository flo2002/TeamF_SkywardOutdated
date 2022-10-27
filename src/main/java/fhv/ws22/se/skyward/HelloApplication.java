package fhv.ws22.se.skyward;

import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.DataGenerator;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HelloApplication extends Application {
    public static DatabaseFacade dbf;
    private static final Logger logger = LogManager.getLogger(HelloApplication.class);


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        DataGenerator.generateData();


        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Doe");

        dbf = new DatabaseFacade();
        dbf.add(john);
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
        launch();
    }
}