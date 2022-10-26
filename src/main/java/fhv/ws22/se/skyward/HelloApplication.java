package fhv.ws22.se.skyward;

import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static DatabaseFacade dbf;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Doe");

        dbf = new DatabaseFacade();
        dbf.add(john);

        launch();
    }

    public static DatabaseFacade getDbf() {
        return dbf;
    }
}