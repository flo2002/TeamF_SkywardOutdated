package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardController extends AbstractController {
    @FXML
    private TableView<BookingDto> arrivalTable;

    @FXML
    private TableView<BookingDto> departureTable;
    @FXML
    private TableColumn<BookingDto, BigInteger> arrivalBookingNumberCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> arrivalCheckInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> arrivalCheckOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, String> arrivalIsCheckedInCol;

    @FXML
    private TableColumn<BookingDto, BigInteger> departureBookingNumberCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> departureCheckInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> departureCheckOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, String> departureIsCheckedInCol;
    @FXML
    protected void initialize() {
        arrivalBookingNumberCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        arrivalCheckInDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckInDateTime().toLocalDate()));
        arrivalCheckOutDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckOutDateTime().toLocalDate()));
        arrivalIsCheckedInCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));

        departureBookingNumberCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        departureCheckInDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckInDateTime().toLocalDate()));
        departureCheckOutDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckOutDateTime().toLocalDate()));
        departureIsCheckedInCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));

        updateData();
        arrivalTable.setRowFactory(bookingDtoTableView -> {
            TableRow<BookingDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BookingDto rowData = row.getItem();
                    session.setTmpBooking(rowData);
                    controllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
                }
            });
            return row;
        });
        departureTable.setRowFactory(bookingDtoTableView -> {
            TableRow<BookingDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BookingDto rowData = row.getItem();
                    session.setTmpBooking(rowData);
                    controllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
                }
            });
            return row;
        });
    }

    public void updateData() {
        arrivalTable.getItems().clear();
        List<BookingDto> arrivalTempBookings = session.getAll(BookingDto.class);
        List<BookingDto> arrivalBookings = new ArrayList<BookingDto>();
        for (int i = 0; i< arrivalTempBookings.size();i++) {
            if (arrivalTempBookings.get(i).getCheckInDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() &&
                    arrivalTempBookings.get(i).getCheckInDateTime().getYear() == LocalDateTime.now().getYear()) {
                arrivalBookings.add(arrivalTempBookings.get(i));
            }
        }
        arrivalTable.getItems().addAll(arrivalBookings);

        departureTable.getItems().clear();
        List<BookingDto> departureTempBookings = session.getAll(BookingDto.class);
        List<BookingDto> departureBookings = new ArrayList<BookingDto>();
        for (int i = 0; i < departureTempBookings.size(); i++) {
            if (departureTempBookings.get(i).getCheckOutDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear() &&
            departureTempBookings.get(i).getCheckOutDateTime().getYear() == LocalDateTime.now().getYear()) {
                departureBookings.add(departureTempBookings.get(i));
            }
        }
        departureTable.getItems().addAll(departureBookings);
    }

    public void onCreateBookingButtonClick(Event event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
}
