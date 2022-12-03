package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.AddressDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCustomerController extends AbstractController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    public Label bNrPlaceholder;

    @FXML
    private void initialize() {
        tmpBooking = session.getTmpBooking();
        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());
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

        CustomerDto customerDto = new CustomerDto(firstNameTextField.getText(), lastNameTextField.getText(), new AddressDto("MainStreet", "43", "1234", "Vienna", "Austria"));
        session.add(customerDto);

        NotificationUtil.getInstance().showSuccessNotification("The guest was added to the database and booking", event);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Search Customer");
    }

    @FXML
    public void onSearchCustomerButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Search");
    }

    public void backButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
}
