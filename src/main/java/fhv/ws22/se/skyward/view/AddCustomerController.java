package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.AddressDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

public class AddCustomerController extends AbstractController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    @FXML
    private void initialize() {
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        tmpBooking = session.getTmpBooking();

        if (firstNameTextField.getText().isEmpty()) {
            firstNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (lastNameTextField.getText().isEmpty()) {
            lastNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }

        CustomerDto customerDto = new CustomerDto(firstNameTextField.getText(), lastNameTextField.getText(), new AddressDto("MainStreet", 43, 1234, "Vienna", "Austria"));
        try {
            session.add(customerDto);

            List<CustomerDto> customers = tmpBooking.getCustomers();
            customers.add(customerDto);
            tmpBooking.setCustomers(customers);

            NotificationUtil.getInstance().showSuccessNotification("The guest was added to the booking", event);
            controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
        } catch (Exception e) {
            try {
                NotificationUtil.getInstance().showErrorNotification(e.getCause().getCause().getCause().getMessage(), event);
            } catch (Exception ex) {
                NotificationUtil.getInstance().showErrorNotification(e.getMessage(), event);
                e.printStackTrace();
            }
        }

        // TODO session update not working?
        //session.update(tmpBooking.getId(), tmpBooking);
    }

    @FXML
    public void onSearchCustomerButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Search");
    }
}
