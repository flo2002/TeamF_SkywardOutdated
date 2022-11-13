package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
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
    private CheckBox filterSingleRoom;
    @FXML
    private CheckBox filterDoubleRoom;
    @FXML
    private CheckBox filterTripleRoom;
    @FXML
    private CheckBox filterTwinRoom;
    @FXML
    private CheckBox filterQueenRoom;

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
        configureListener();
        updateData();
    }

    private void configureListener() {
        if (session.getFilterMap().size() == 0) {
            HashMap<String, Boolean> filterMap = new HashMap<String, Boolean>();
            filterMap.put("Single", false);
            filterMap.put("Double", false);
            filterMap.put("Triple", false);
            filterMap.put("Twin", false);
            filterMap.put("Queen", false);
            session.setFilterMap(filterMap);
        }
        HashMap<String, Boolean> filterMap = session.getFilterMap();

        filterSingleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Single", filterSingleRoom.isSelected());
            session.setFilterMap(filterMap);
            updateData();
        });
        filterDoubleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Double", filterDoubleRoom.isSelected());
            session.setFilterMap(filterMap);
            updateData();
        });
        filterTripleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Triple", filterTripleRoom.isSelected());
            session.setFilterMap(filterMap);
            updateData();
        });
        filterTwinRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Twin", filterTwinRoom.isSelected());
            session.setFilterMap(filterMap);
            updateData();
        });
        filterQueenRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Queen", filterQueenRoom.isSelected());
            session.setFilterMap(filterMap);
            updateData();
        });
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

    public void updateData() {

        HashMap<String, Boolean> filterMap = session.getFilterMap();
        if (filterMap.get("Single")) {
            filterSingleRoom.setSelected(true);
        }
        if (filterMap.get("Double")) {
            filterDoubleRoom.setSelected(true);
        }
        if (filterMap.get("Triple")) {
            filterTripleRoom.setSelected(true);
        }
        if (filterMap.get("Twin")) {
            filterTwinRoom.setSelected(true);
        }
        if (filterMap.get("Queen")) {
            filterQueenRoom.setSelected(true);
        }

        roomTable.getItems().clear();


        List<RoomDto> rooms = session.getAvailableRooms();
        if (rooms != null) {
            for (RoomDto room : rooms) {
                roomTable.getItems().add(room);
            }
        }
    }
}
