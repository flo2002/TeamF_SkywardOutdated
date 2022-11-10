package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.Session;
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
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

public class AddRoomController {
    private static final Logger logger = LogManager.getLogger("AddRoomController");
    private Session session;
    private BookingDto tmpBooking;

    @FXML
    private TableView<RoomDto> roomTable;
    @FXML
    private TableColumn<RoomDto, Integer> roomNumberCol;
    @FXML
    private TableColumn<RoomDto, String> roomTypeNameCol;
    @FXML
    private TableColumn<RoomDto, BigDecimal> roomTypePriceCol;
    @FXML
    private TableColumn<RoomDto, String> roomStateNameCol;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession();

        roomNumberCol.setCellValueFactory(new PropertyValueFactory<RoomDto, Integer>("roomNumber"));
        roomTypeNameCol.setCellValueFactory(new PropertyValueFactory<RoomDto, String>("roomTypeName"));
        roomTypePriceCol.setCellValueFactory(new PropertyValueFactory<RoomDto, BigDecimal>("roomTypePrice"));
        roomStateNameCol.setCellValueFactory(new PropertyValueFactory<RoomDto, String>("roomStateName"));

        tmpBooking = session.getTmpBooking();
        roomTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<RoomDto> selectedRooms = roomTable.getSelectionModel().getSelectedItems();
                tmpBooking.setRooms(selectedRooms);
            }
        });

        updateTable();
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/bookings.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Booking");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
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
            logger.error("objects: AddRoomController, msg: " + e.getMessage());

            e.printStackTrace();
        }
    }

    public void updateTable() {
        roomTable.getItems().clear();
        List<RoomDto> rooms = session.getAvailableRooms(RoomDto.class);
        for (RoomDto room : rooms) {
            roomTable.getItems().add(room);
        }
    }

}
