package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Room;
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
    private TableView<RoomDto> table;

    @FXML
    protected void initialize() {
        List<TableColumn<RoomDto, String>> columns = new ArrayList<>();

        TableColumn<RoomDto, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomNumberCol.setPrefWidth(100);
        columns.add(roomNumberCol);

        /*for (int i = 0; i < 5; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
            TableColumn<Map, String> column = new TableColumn<>(LocalDate.now().plusDays(i).format(formatter));
            column.setCellValueFactory(entry -> new SimpleObjectProperty<>());
            column.setMinWidth(20);
            columns.add(column);
        }*/

        for (TableColumn<RoomDto, String> col : columns) {
            table.getColumns().add(col);
        }

        updateData();
    }

    public void updateData() {
        table.getItems().clear();
        List<RoomDto> rooms = session.getAll(RoomDto.class);
        table.getItems().addAll(rooms);
    }

}
