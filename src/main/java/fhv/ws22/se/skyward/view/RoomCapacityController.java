package fhv.ws22.se.skyward.view;

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
    private TableView table;

    @FXML
    protected void initialize() {
        List<TableColumn> columns = new ArrayList<>();

        TableColumn<RoomDto, String> roomNumberCol = new TableColumn<>("Room Number");
        roomNumberCol.setCellValueFactory(entry -> {
            String roomNumber = Integer.valueOf(entry.getValue().getRoomNumber()).toString();
            return new SimpleObjectProperty<>(roomNumber);
        });
        roomNumberCol.setPrefWidth(100);
        columns.add(roomNumberCol);

        for (int i = 0; i < 5; i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
            TableColumn<Map, String> column = new TableColumn<>(LocalDate.now().plusDays(i).format(formatter));
            column.setCellValueFactory(entry -> new SimpleObjectProperty<>());
            column.setMinWidth(20);
            columns.add(column);
        }

        for (TableColumn col : columns) {
            table.getColumns().add(col);
        }

        for (RoomDto room : (List<RoomDto>) session.getAll(RoomDto.class)) {
            table.getItems().add("asdf");
        }
    }


}
