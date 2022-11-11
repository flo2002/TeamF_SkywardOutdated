package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        updateTable();

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
    public void onConfirmCustomerSearchButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-guests.fxml", "Home");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        System.out.println("This works, right?");
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    public void updateTable() {
        customerTable.getItems().clear();
        List<CustomerDto> customers = session.getAll(CustomerDto.class);
        for (CustomerDto customer : customers) {
            customerTable.getItems().add(customer);
        }
    }
}
