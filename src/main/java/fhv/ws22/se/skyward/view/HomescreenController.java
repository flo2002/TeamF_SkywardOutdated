package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.Session;
import fhv.ws22.se.skyward.domain.SessionFactory;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class HomescreenController {
    private Session session;
    private static final BigInteger clientSessionID = new BigInteger("1");

    @FXML
    private TableView<BookingDto> table;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, Boolean> isCheckedInCol;

    private ObservableList<BookingDto> tablelist;

    @FXML
    protected void initialize() {
        session = SessionFactory.getInstance().getSession(clientSessionID);

        checkInDateTimeCol.setCellValueFactory(new PropertyValueFactory<BookingDto, LocalDateTime>("checkInDateTime"));
        checkOutDateTimeCol.setCellValueFactory(new PropertyValueFactory<BookingDto, LocalDateTime>("checkOutDateTime"));
        isCheckedInCol.setCellValueFactory(new PropertyValueFactory<BookingDto, Boolean>("isCheckedIn"));

        updateTable();
        table.setRowFactory(bookingDtoTableView -> {
            TableRow<BookingDto> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && (! row.isEmpty()) ) {
                    BookingDto rowData = row.getItem();
                    session.setTmpBooking(rowData);
                    ControllerNavigationUtil.navigate(mouseEvent,"src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
                }
            });
            return row;
        });
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
        table.getItems().clear();
        List<BookingDto> bookings = session.getAll(BookingDto.class);
        for (BookingDto booking : bookings) {
            table.getItems().add(booking);
        }
        tablelist = table.getItems();
    }
}
