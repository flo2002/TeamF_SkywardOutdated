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
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class BookingController {
    private static final Logger logger = LogManager.getLogger("BookingController");
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
        session = SessionFactory.getInstance().getSession();
        tmpBooking = session.getTmpBooking();

        HashMap<String, Boolean> filtermap = new HashMap<String, Boolean>();
        filtermap.put("Single", filterSingleRoom.isSelected());
        filtermap.put("Double", filterDoubleRoom.isSelected());
        filtermap.put("Triple", filterTripleRoom.isSelected());
        filtermap.put("Twin", filterTwinRoom.isSelected());
        filtermap.put("Queen", filterQueenRoom.isSelected());
        session.setFilterMap(filtermap);

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
        HashMap<String, Boolean> filtermap = session.getFilterMap();

        filterSingleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filtermap.put("Single", filterSingleRoom.isSelected());
            session.setFilterMap(filtermap);
            updateData();
        });
        filterDoubleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filtermap.put("Double", filterDoubleRoom.isSelected());
            session.setFilterMap(filtermap);
            updateData();
        });
        filterTripleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filtermap.put("Triple", filterTripleRoom.isSelected());
            session.setFilterMap(filtermap);
            updateData();
        });
        filterTwinRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filtermap.put("Twin", filterTwinRoom.isSelected());
            session.setFilterMap(filtermap);
            updateData();
        });
        filterQueenRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filtermap.put("Queen", filterQueenRoom.isSelected());
            session.setFilterMap(filtermap);
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
        session.update(tmpBooking.getId(), tmpBooking);
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

    @FXML
    public void onAddRoomButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/add-rooms.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Rooms");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddGuestButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/add-guests.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.setTitle("Guests");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            logger.error("objects: BookingController, msg: " + e.getMessage());

            e.printStackTrace();
        }
    }

    public void updateData() {
        HashMap<String, Boolean> filtermap = session.getFilterMap();
        if (filtermap.get("Single")) {
            filterSingleRoom.setSelected(true);
        } else if (filtermap.get("Double")) {
            filterDoubleRoom.setSelected(true);
        } else if (filtermap.get("Triple")) {
            filterTripleRoom.setSelected(true);
        } else if (filtermap.get("Twin")) {
            filterTwinRoom.setSelected(true);
        } else if (filtermap.get("Queen")) {
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
