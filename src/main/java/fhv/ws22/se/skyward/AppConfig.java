package fhv.ws22.se.skyward;

import com.google.inject.AbstractModule;
import fhv.ws22.se.skyward.domain.DataService;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.view.ControllerService;
import fhv.ws22.se.skyward.view.FXMLLoaderProvider;
import fhv.ws22.se.skyward.view.HomescreenController;
import fhv.ws22.se.skyward.view.SessionService;
import javafx.fxml.FXMLLoader;

public class AppConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataService.class).to(DatabaseFacade.class);
        bind(SessionService.class).to(Session.class);
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(ControllerService.class).to(HomescreenController.class);
    }
}
