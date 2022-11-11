package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class AddRoomController {
    private static final Logger logger = LogManager.getLogger("AddRoomController");
    private static final BigInteger clientSessionID = new BigInteger("1");
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
        session = SessionFactory.getInstance().getSession(clientSessionID);

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
        NotificationUtil.getInstance().showSuccessNotification("The Rooms were added to the booking", event);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
        }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onBookingButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
    public void updateTable() {
        roomTable.getItems().clear();

        List<RoomDto> rooms = session.getAvailableRooms(tmpBooking.getCheckInDateTime(), tmpBooking.getCheckOutDateTime());
        for (RoomDto room : rooms) {
            roomTable.getItems().add(room);
        }
    }
}
