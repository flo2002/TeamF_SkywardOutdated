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
        fxmlLoader.setLocation(getClass().getResource("dashboard.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("SkyWard");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(windowEvent -> {
            System.exit(0);
        });

        try {
            stage.getIcons().add(new Image(getClass().getResource("icons/SkyWardIcon.png").toURI().toString()));
        } catch (Exception e) {
            logger.error("Could not load icon", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}