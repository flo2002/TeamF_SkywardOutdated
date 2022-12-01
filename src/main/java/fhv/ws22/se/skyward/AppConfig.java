package fhv.ws22.se.skyward;

import com.google.inject.AbstractModule;
import fhv.ws22.se.skyward.domain.DataService;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.view.*;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.FXMLLoaderProvider;
import fhv.ws22.se.skyward.view.util.NavigationService;
import jakarta.persistence.EntityManager;
import javafx.fxml.FXMLLoader;

public class AppConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(DataService.class).to(DatabaseFacade.class);
        bind(SessionService.class).to(Session.class);
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(NavigationService.class).to(ControllerNavigationUtil.class);
    }
}
