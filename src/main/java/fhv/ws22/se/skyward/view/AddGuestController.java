package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddGuestController {
    private static final Logger logger = LogManager.getLogger("AddGuestController");

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onSearchCustomerButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Search");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onAddAnotherCustomerButtonClick(ActionEvent event) {

    }
}
