package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

public class SearchCustomerController extends AbstractController {
    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<CustomerDto> customerTable;
    @FXML
    private TableColumn<CustomerDto, String> firstNameCol;
    @FXML
    private TableColumn<CustomerDto, String> lastNameCol;
    @FXML
    public Label bNrPlaceholder;


    @FXML
    protected void initialize() {
        firstNameCol.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastName"));

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTable(newValue);
        });

        updateTable("");

        tmpBooking = session.getTmpBooking();
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<CustomerDto> selectedCustomers = customerTable.getSelectionModel().getSelectedItems();
                tmpBooking.setCustomers(selectedCustomers);
            }
        });
        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());
    }

    @FXML
    public void onConfirmCustomerSearchButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        NotificationUtil.getInstance().showSuccessNotification("The guests were added to the booking", event);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onCreateButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-customers.fxml", "Home");
    }

    @FXML
    public void backButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }


    public void updateTable(String filter) {
        customerTable.getItems().clear();
        List<CustomerDto> customers = session.getAll(CustomerDto.class);
        for (CustomerDto customer : customers) {
            if (customer.getFirstName().toLowerCase().contains(filter.toLowerCase()) || customer.getLastName().toLowerCase().contains(filter.toLowerCase())) {
                customerTable.getItems().add(customer);
            }
        }
    }
}
