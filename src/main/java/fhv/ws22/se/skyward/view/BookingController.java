package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.model.DTOs.BookingDto;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        /*BookingDto booking = new BookingDto(LocalDateTime.now(), LocalDateTime.now(), false, null, null);
        dbf.add(booking);
        updateTable();*/
        try {
            URL url = new File("src/main/resources/fhv/ws22/se/skyward/create-bookings.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Home");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateTable() {
        table.getItems().clear();
        List<BookingDto> bookings = dbf.getAll(BookingDto.class);
        for (BookingDto booking : bookings) {
            table.getItems().add(booking);
        }
    }
}
