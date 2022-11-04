package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.model.DTOs.BookingDto;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class BookingController {
    private DatabaseFacade dbf;
    @FXML
    private TableView<BookingDto> table;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkInDateTimeCol;
    @FXML
    private TableColumn<BookingDto, LocalDateTime> checkOutDateTimeCol;
    @FXML
    private TableColumn<BookingDto, Boolean> isCheckedInCol;


    @FXML
    protected void initialize() {
        dbf = DatabaseFacade.getInstance();
        checkInDateTimeCol.setCellValueFactory(new PropertyValueFactory<BookingDto, LocalDateTime>("checkInDateTime"));
        checkOutDateTimeCol.setCellValueFactory(new PropertyValueFactory<BookingDto, LocalDateTime>("checkOutDateTime"));
        isCheckedInCol.setCellValueFactory(new PropertyValueFactory<BookingDto, Boolean>("isCheckedIn"));
        updateTable();
    }

    @FXML
    public void onCreateBookingButtonClick(ActionEvent event) {
        BookingDto booking = new BookingDto(LocalDateTime.now(), LocalDateTime.now(), false, null, null);
        dbf.add(booking);
        updateTable();
    }

    public void updateTable() {
        table.getItems().clear();
        List<BookingDto> bookings = dbf.getAll(BookingDto.class);
        for (BookingDto booking : bookings) {
            table.getItems().add(booking);
        }
    }
}
