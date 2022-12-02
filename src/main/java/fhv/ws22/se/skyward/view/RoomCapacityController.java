package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.entity.Room;
import fhv.ws22.se.skyward.view.util.RoomCapacity;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomCapacityController extends AbstractController {
    @FXML
    private TableView<RoomCapacity> table;

    private Boolean isOccupied(RoomDto askedRoom, LocalDate date) {
        List<RoomDto> availableRooms = session.getAvailableRooms(LocalDateTime.of(date, LocalTime.now()), LocalDateTime.of(date.plusDays(1), LocalTime.now()));
        for (RoomDto room : availableRooms) {
            if (room.getId().equals(askedRoom.getId())) {
                return true;
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
            roomCap.setDay1(isOccupied(room, LocalDate.now()));
            roomCap.setDay2(isOccupied(room, LocalDate.now().plusDays(1)));
            roomCap.setDay3(isOccupied(room, LocalDate.now().plusDays(2)));
            roomCap.setDay4(isOccupied(room, LocalDate.now().plusDays(3)));
            roomCap.setDay5(isOccupied(room, LocalDate.now().plusDays(4)));

            roomCaps.add(roomCap);
        }

        TableColumn<RoomCapacity, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        List<TableColumn<RoomCapacity, String>> columns = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            TableColumn<RoomCapacity, String> column = new TableColumn<>(LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("dd.MM")));
            column.setCellValueFactory(entry -> new SimpleObjectProperty<>(entry.getValue().getDay1() ? "occupied" : "free"));
            column.setCellFactory(col -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item);
                        if (item.equals("occupied")) {
                            setTextFill(Color.RED);
                            setStyle("-fx-background-color: #ff0000");
                        } else {
                            setTextFill(Color.GREEN);
                            setStyle("-fx-background-color: #00ff00");
                        }
                    }
                }
            });
            columns.add(column);
        }

        table.getColumns().addAll(roomNumberCol, columns.get(0), columns.get(1), columns.get(2), columns.get(3), columns.get(4), columns.get(5));
        table.getItems().addAll(roomCaps);
    }
}
