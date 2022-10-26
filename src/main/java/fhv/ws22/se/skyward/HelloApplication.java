package fhv.ws22.se.skyward;

import fhv.ws22.se.skyward.model.Person;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        /*Person john = new Person();
        john.setFirstName("John");
        john.setLastName("Doe");

        DatabaseFacade dbf = new DatabaseFacade();
        dbf.addPerson(john);*/

        launch();
    }
}