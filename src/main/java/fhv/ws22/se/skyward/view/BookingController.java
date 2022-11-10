package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.Session;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BookingController {
    private static final Logger logger = LogManager.getLogger("BookingController");
    private static final BigInteger clientSessionID = new BigInteger("1");
    private Session session;
    private BookingDto tmpBooking;

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
    private DatePicker checkInDatePicker;
    @FXML
    private DatePicker checkOutDatePicker;

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
    private TableView<CustomerDto> customerTable;
    @FXML
    private TableColumn<CustomerDto, String> firstNameCol;
    @FXML
    private TableColumn<CustomerDto, String> lastNameCol;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);
        tmpBooking = session.getTmpBooking();

        roomNumberCol.setCellValueFactory(new PropertyValueFactory<RoomDto, Integer>("roomNumber"));
        roomTypeNameCol.setCellValueFactory(new PropertyValueFactory<RoomDto, String>("roomTypeName"));
        roomTypePriceCol.setCellValueFactory(new PropertyValueFactory<RoomDto, BigDecimal>("roomTypePrice"));
        roomStateNameCol.setCellValueFactory(new PropertyValueFactory<RoomDto, String>("roomStateName"));

        firstNameCol.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastName"));

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


        checkInDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDateTime checkInDateTime = LocalDateTime.of(newValue.getYear(), newValue.getMonth(), newValue.getDayOfMonth(), 15, 0);
                tmpBooking.setCheckInDateTime(checkInDateTime);
                updateData();
            }
        });
        checkOutDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDateTime checkOutDateTime = LocalDateTime.of(newValue.getYear(), newValue.getMonth(), newValue.getDayOfMonth(), 11, 0);
                tmpBooking.setCheckOutDateTime(checkOutDateTime);
                updateData();
            }
        });
    }

    @FXML
    public void onCreateBookingButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
        //NotificationController.getInstance().showSuccessNotification("Home", stage);
    }

    @FXML
    public void onAddRoomButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-rooms.fxml", "Rooms");
    }

    @FXML
    public void onAddGuestButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-guests.fxml", "Guests");
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

        if (tmpBooking.getCheckInDateTime() != null) {
            checkInDatePicker.setValue(tmpBooking.getCheckInDateTime().toLocalDate());
        }
        if (tmpBooking.getCheckOutDateTime() != null) {
            checkOutDatePicker.setValue(tmpBooking.getCheckOutDateTime().toLocalDate());
        }

        customerTable.getItems().clear();
        List<CustomerDto> customers = tmpBooking.getCustomers();
        if (customers != null) {
            for (CustomerDto customer : customers) {
                customerTable.getItems().add(customer);
            }
        }

        roomTable.getItems().clear();
        List<RoomDto> rooms = tmpBooking.getRooms();
        if (rooms != null) {
            for (RoomDto room : rooms) {
                roomTable.getItems().add(room);
            }
        }
    }
}
