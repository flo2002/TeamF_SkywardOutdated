package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Room;
import fhv.ws22.se.skyward.view.util.RoomCapacity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomCapacityController extends AbstractController {
    @FXML
    private TableView<RoomCapacity> table;

    private Boolean isOccupied(List<BookingDto> bookings, RoomDto room, LocalDate date) {
        //session.getAvailableRooms(date, date);
        return false;
    }

    @FXML
    protected void initialize() {
        List<RoomDto> rooms = session.getAll(RoomDto.class);
        List<BookingDto> bookings = session.getAll(BookingDto.class);
        List<RoomCapacity> roomCaps = new ArrayList<>();

        for (RoomDto room : rooms) {
            RoomCapacity roomCap = new RoomCapacity();

            roomCap.setRoomNumber(room.getRoomNumber());
            roomCap.setDay1(isOccupied(bookings, room, LocalDate.now()));
            roomCap.setDay2(isOccupied(bookings, room, LocalDate.now().plusDays(1)));
            roomCap.setDay3(isOccupied(bookings, room, LocalDate.now().plusDays(2)));
            roomCap.setDay4(isOccupied(bookings, room, LocalDate.now().plusDays(3)));
            roomCap.setDay5(isOccupied(bookings, room, LocalDate.now().plusDays(4)));

            roomCaps.add(roomCap);
        }

        TableColumn<RoomCapacity, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<RoomCapacity, String> day1Col = new TableColumn<>("Day 1");
        day1Col.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay1() ? "occupied" : "free"));

        TableColumn<RoomCapacity, Boolean> day2Col = new TableColumn<>("Day 2");
        day2Col.setCellValueFactory(new PropertyValueFactory<>("day2"));

        TableColumn<RoomCapacity, Boolean> day3Col = new TableColumn<>("Day 3");
        day3Col.setCellValueFactory(new PropertyValueFactory<>("day3"));

        TableColumn<RoomCapacity, Boolean> day4Col = new TableColumn<>("Day 4");
        day4Col.setCellValueFactory(new PropertyValueFactory<>("day4"));

        TableColumn<RoomCapacity, Boolean> day5Col = new TableColumn<>("Day 5");
        day5Col.setCellValueFactory(new PropertyValueFactory<>("day5"));

        table.getColumns().addAll(roomNumberCol, day1Col, day2Col, day3Col, day4Col, day5Col);
        table.getItems().addAll(roomCaps);
    }
}
