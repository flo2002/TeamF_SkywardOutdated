package fhv.ws22.se.skyward;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.persistence.DataGenerator;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class MainApplication extends Application {
    private static final Logger logger = LogManager.getLogger("SkyWard");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("homescreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 770,530);
        stage.getIcons().add(new Image("SkyWardIcon.png"));
        stage.setTitle("SkyWard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppConfig());

        Session session = SessionFactory.getInstance().getSession(new BigInteger("1"));
        DataGenerator dataGenerator = new DataGenerator();
        injector.injectMembers(session);
        injector.injectMembers(dataGenerator);

        dataGenerator.generateData();
        launch();
    }
}