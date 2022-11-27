package fhv.ws22.se.skyward.view;

import com.google.inject.Inject;
import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class DashboardController extends AbstractController {
    @FXML
    private TableView<BookingDto> table;

    @FXML
    private TableView<BookingDto> table1;
    @FXML
    private TableColumn<BookingDto, BigInteger> bookingNumberCol;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, String> isCheckedInCol;

    @FXML
    private TableColumn<BookingDto, BigInteger> bookingNumberCol1;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkInDateTimeCol1;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkOutDateTimeCol1;
    @FXML
    private TableColumn<BookingDto, String> isCheckedInCol1;
    @FXML
    protected void initialize() {
        bookingNumberCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        checkInDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("checkInDateTime"));
        checkOutDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDateTime"));
        isCheckedInCol.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));

        bookingNumberCol1.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        checkInDateTimeCol1.setCellValueFactory(new PropertyValueFactory<>("checkInDateTime"));
        checkOutDateTimeCol1.setCellValueFactory(new PropertyValueFactory<>("checkOutDateTime"));
        isCheckedInCol1.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getIsCheckedIn() ? "Checked-In" : "Checked-Out"));

        updateData();
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
    }


    public void updateData() {
        table.getItems().clear();
        List<BookingDto> bookings = session.getAll(BookingDto.class);
        table.getItems().addAll(bookings);

        table1.getItems().clear();
        List<BookingDto> bookings1 = session.getAll(BookingDto.class);
        table1.getItems().addAll(bookings1);
    }
}
