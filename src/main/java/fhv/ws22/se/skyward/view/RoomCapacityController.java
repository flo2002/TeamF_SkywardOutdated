package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;
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

    private Boolean isOccupied(LocalDate date) {
        List<BookingDto> bookings = (List<BookingDto>) session.getAll(BookingDto.class);
        // check if any booking is in the same time frame to remove it from the available rooms
        for (BookingDto booking : bookings) {
            //if (booking.getCheckInDateTime().toLocalDate().isBefore(date) || booking.getCheckOutDateTime().toLocalDate().isAfter(date)) {
            if (date.isBefore(booking.getCheckOutDateTime().toLocalDate()) && date.isAfter(booking.getCheckInDateTime().toLocalDate())) {
                List<RoomDto> blockedRooms = booking.getRooms();
                if (blockedRooms != null) {
                    for (RoomDto room : blockedRooms) {
                        if (room.getRoomNumber().equals(room.getRoomNumber())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @FXML
    protected void initialize() {
        List<RoomDto> rooms = session.getAll(RoomDto.class);
        List<RoomCapacity> roomCaps = new ArrayList<>();

        for (RoomDto room : rooms) {
            RoomCapacity roomCap = new RoomCapacity();

            roomCap.setRoomNumber(room.getRoomNumber());
            roomCap.setDay1(isOccupied(LocalDate.now()));
            roomCap.setDay2(isOccupied(LocalDate.now().plusDays(1)));
            roomCap.setDay3(isOccupied(LocalDate.now().plusDays(2)));
            roomCap.setDay4(isOccupied(LocalDate.now().plusDays(3)));
            roomCap.setDay5(isOccupied(LocalDate.now().plusDays(4)));

            roomCaps.add(roomCap);
        }

        TableColumn<RoomCapacity, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        List<TableColumn<RoomCapacity, String>> columns = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            TableColumn<RoomCapacity, String> column = new TableColumn<>(LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("dd.MM")));
            column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay1() ? "occupied" : "free"));
            columns.add(column);
        }

        table.getColumns().addAll(roomNumberCol, columns.get(0), columns.get(1), columns.get(2), columns.get(3), columns.get(4), columns.get(5));
        table.getItems().addAll(roomCaps);
    }
}
