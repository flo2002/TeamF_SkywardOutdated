package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.model.DTOs.BookingDto;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class BookingController {
    private DatabaseFacade dbf;
    @FXML
    private TableView<BookingDto> bookingsTable;
    /*@FXML
    private TableColumn<BookingDto, String> roomNumbersCol;
    @FXML
    private TableColumn<BookingDto, String> firstNameCol;
    @FXML
    private TableColumn<BookingDto, String> lastNameCol;
    @FXML
    private TableColumn<BookingDto, Boolean> statusCol;*/

    public BookingController() {
        dbf = DatabaseFacade.getInstance();
        List<BookingDto> bookings = (List<BookingDto>) dbf.getAll(BookingDto.class);

        bookingsTable = new TableView();

        /*TableColumn<BookingDto, LocalDateTime> checkinColumn =
                TableColumn<>("CheckIn");
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));*/



        //https://jenkov.com/tutorials/javafx/tableview.html#add-data-to-tableview


        for (BookingDto booking : bookings) {
            bookingsTable.getItems().add(booking);
        }
    }

    @FXML
    public void onCreateBookingButtonClick() {
        System.out.println("Let's create a Booking!");
    }
}
