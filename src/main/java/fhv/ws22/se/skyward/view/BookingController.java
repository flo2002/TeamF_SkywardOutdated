package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.Session;

import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class BookingController {
    private static final Logger logger = LogManager.getLogger("BookingController");
    private static final BigInteger clientSessionID = new BigInteger("1");
    private Session session;
    private BookingDto tmpBooking;


    @FXML
    private Button checkInCheckOutButton;


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
        roomStateNameCol.setCellValueFactory(new PropertyValueFactory<RoomDto, String>("roomStateName"));

        firstNameCol.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastName"));

        configureListener();

        updateData();
    }

    private void configureListener() {
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
    public void onCheckInCheckOutButtonClick(ActionEvent actionEvent) {
        if (checkInCheckOutButton.getText().equals("Check-in")) {
            tmpBooking.setIsCheckedIn(true);
            updateData();
            checkInCheckOutButton.setText("Check-out");
        } else if (checkInCheckOutButton.getText().equals("Check-out")){
            tmpBooking.setIsCheckedIn(false);
            updateData();
            checkInCheckOutButton.setText("Check-in");
        }
    }

    @FXML
    public void onCreateBookingButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        session.resetTmpBooking();
        NotificationUtil.getInstance().showSuccessNotification("The Booking was saved", event);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void onHomeButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/homescreen.fxml", "Home");
    }

    @FXML
    public void onInvoicePageButtonClick(ActionEvent event) {
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-overview.fxml", "Invoice");
    }


    @FXML
    public void onAddRoomButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);

        if (tmpBooking.getCheckInDateTime() == null || tmpBooking.getCheckOutDateTime() == null) {
            NotificationUtil.getInstance().showErrorNotification("Please select a Check-in and Check-out date", event);
            return;
        }

        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/add-rooms.fxml", "Rooms");
    }

    @FXML
    public void onAddGuestButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/search-customer.fxml", "Guests");
    }

    @FXML
    public void onInvoiceButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        ControllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/invoice-information.fxml", "Invoice");
    }

    public void updateData() {
        if (tmpBooking.getIsCheckedIn() != null && tmpBooking.getIsCheckedIn()) {
            checkInCheckOutButton.setText("Check-out");
        }
        if (tmpBooking.getIsCheckedIn() != null && !tmpBooking.getIsCheckedIn()) {
            checkInCheckOutButton.setText("Check-in");
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
