package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AddGuestController {
    private static final Logger logger = LogManager.getLogger("AddGuestController");
    private static final BigInteger clientSessionID = new BigInteger("1");
    private Session session;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    @FXML
    private void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        if (firstNameTextField.getText().isEmpty()) {
            firstNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (lastNameTextField.getText().isEmpty()) {
            lastNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }

        CustomerDto customerDto = new CustomerDto(firstNameTextField.getText(), lastNameTextField.getText(), "MainStreet", "43", "1234", "Vienna", "Austria");
        session.add(customerDto);

        NotificationUtil.getInstance().showSuccessNotification("The guest was added to the database and booking", event);
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
    public void onBookingButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
}
