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
    private TextField streetTextField;

    @FXML
    private TextField zipTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private void initialize() {
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        String regex = "^[0-9]{4,5}(?:-[0-9]{1,4})?$";

        tmpBooking = session.getTmpBooking();

        if (firstNameTextField.getText().isEmpty()) {
            firstNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (lastNameTextField.getText().isEmpty()) {
            lastNameTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (streetTextField.getText().isEmpty()) {
            streetTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (zipTextField.getText().isEmpty()) {
            zipTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (countryTextField.getText().isEmpty()) {
            countryTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (numberTextField.getText().isEmpty()) {
            numberTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }
        if (cityTextField.getText().isEmpty()) {
            cityTextField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            return;
        }

        CustomerDto customerDto = new CustomerDto(firstNameTextField.getText(), lastNameTextField.getText(),
                new AddressDto(streetTextField.getText(), Integer.parseInt(numberTextField.getText()), Integer.parseInt(zipTextField.getText()),
                        cityTextField.getText(), countryTextField.getText()));


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
