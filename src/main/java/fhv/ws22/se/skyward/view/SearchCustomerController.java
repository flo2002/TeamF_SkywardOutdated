package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

public class SearchCustomerController {
    private static final Logger logger = LogManager.getLogger("SearchCustomerController");
    private static final BigInteger clientSessionID = new BigInteger("1");
    private Session session;
    private BookingDto tmpBooking;

    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<CustomerDto> customerTable;
    @FXML
    private TableColumn<CustomerDto, String> firstNameCol;
    @FXML
    private TableColumn<CustomerDto, String> lastNameCol;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);

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
    }

    @FXML
    public void onSearchButtonClick(ActionEvent event) {
        updateTable(searchTextField.getText());
    }

    @FXML
    public void onConfirmCustomerSearchButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        NotificationUtil.getInstance().showSuccessNotification("The guests were added to the booking", event);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onInvoicePageButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }

    @FXML
    public void onCreateButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-guests.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
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
