package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public class DashboardController extends AbstractController {
    @FXML
    private TableView<BookingDto> table;
    @FXML
    private TextField searchTextField;

    @FXML
    private TableView<BookingDto> table1;
    @FXML
    private TableColumn<BookingDto, BigInteger> bookingNumberCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> checkInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDate> checkOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, String> isCheckedInCol;

    @FXML
    private TableColumn<BookingDto, BigInteger> bookingNumberCol1;
    @FXML
    private TableColumn<BookingDto, LocalDate> checkInDateTimeCol1;
    @FXML
    private TableColumn<BookingDto, LocalDate> checkOutDateTimeCol1;
    @FXML
    private TableColumn<BookingDto, String> isCheckedInCol1;
    @FXML
    protected void initialize() {
        
        bookingNumberCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        checkInDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckInDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckInDateTime().toLocalDate()));
        checkOutDateTimeCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckOutDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckOutDateTime().toLocalDate()));
        isCheckedInCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));
        checkInDateTimeCol.setSortType(TableColumn.SortType.DESCENDING);

        bookingNumberCol1.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        checkInDateTimeCol1.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckInDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckInDateTime().toLocalDate()));
        checkOutDateTimeCol1.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getCheckOutDateTime() == null ? LocalDate.of(1970, 1, 1) : entry.getValue().getCheckOutDateTime().toLocalDate()));
        isCheckedInCol1.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));
        checkOutDateTimeCol1.setSortType(TableColumn.SortType.DESCENDING);

        table.setRowFactory(bookingDtoTableView -> {
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
        table1.setRowFactory(bookingDtoTableView -> {
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

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateData(newValue);
        });
        updateData("");
    }

    public void updateData(String filter) {
        table.getItems().clear();
        List<BookingDto> bookings = session.getAll(BookingDto.class);
        if (filter != null && !filter.isEmpty()) {
            bookings.removeIf(bookingDto -> bookingDto.getCustomers().stream().noneMatch(customerDto -> {
                return customerDto.getFirstName().toLowerCase().contains(filter.toLowerCase()) || customerDto.getLastName().toLowerCase().contains(filter.toLowerCase());
            }));
        }
        table.getItems().addAll(bookings);
        table.getSortOrder().setAll(checkInDateTimeCol);

        table1.getItems().clear();
        List<BookingDto> bookings1 = session.getAll(BookingDto.class);
        if (filter != null && !filter.isEmpty()) {
            bookings1.removeIf(booking -> booking.getCustomers().stream().noneMatch(customerDto -> {
                return customerDto.getFirstName().toLowerCase().contains(filter.toLowerCase()) || customerDto.getLastName().toLowerCase().contains(filter.toLowerCase());
            }));
        }
        table1.getItems().addAll(bookings1);
        table1.getSortOrder().setAll(checkOutDateTimeCol1);
    }

    public void onCreateBookingButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }
}
