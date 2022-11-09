package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SearchCustomerController {
    private static final Logger logger = LogManager.getLogger("SearchCustomerController");
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
        session = SessionFactory.getInstance().getSession();

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
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/add-guests.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            logger.error("objects: SearchCustomerController, msg: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        System.out.println("This works, right?");
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/homescreen.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();

            //NotificationController.getInstance().showSuccessNotification("Home", stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTable() {
        customerTable.getItems().clear();
        List<CustomerDto> customers = session.getAll(CustomerDto.class);
        for (CustomerDto customer : customers) {
            customerTable.getItems().add(customer);
        }
    }
}
