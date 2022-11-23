package fhv.ws22.se.skyward;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.persistence.DataGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class MainApplication extends Application {
    private static final Logger logger = LogManager.getLogger("SkyWard");

    @Override
    public void start(Stage stage) throws IOException {
        Session session = SessionFactory.getInstance().getSession(new BigInteger("1"));
        DataGenerator dataGenerator = new DataGenerator();

        Injector injector = Guice.createInjector(new AppConfig());
        injector.injectMembers(session);
        injector.injectMembers(dataGenerator);

        dataGenerator.generateData();

        FXMLLoader fxmlLoader = injector.getInstance(FXMLLoader.class);

        try (InputStream inputStream = getClass().getResourceAsStream("homescreen.fxml")) {
            System.out.println(inputStream);
            Parent parent = fxmlLoader.load(inputStream);
            Scene scene = new Scene(parent, 770,530);
            stage.getIcons().add(new Image("fhv/ws22/se/Icons/SkyWardIcon.png"));
            stage.setTitle("SkyWard");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(windowEvent -> {
                System.exit(0);
            });
        }
    }

    public static void main(String[] args) {
        launch();
    }
}