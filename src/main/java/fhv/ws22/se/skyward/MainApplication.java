package fhv.ws22.se.skyward;

import com.google.inject.Guice;
import com.google.inject.Injector;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.persistence.DataGenerator;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.view.HomescreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
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
        injector.injectMembers(new HomescreenController());

        dataGenerator.generateData();

        FXMLLoader fxmlLoader = injector.getInstance(FXMLLoader.class);

        try (InputStream fxmlInputStream = getClass().getResourceAsStream("homescreen.fxml")) {
            Parent parent = fxmlLoader.load(fxmlInputStream);
            Scene scene = new Scene(parent, 770,530);
            //stage.getIcons().add(new Image("SkyWardIcon.png"));
            stage.setTitle("SkyWard");
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}