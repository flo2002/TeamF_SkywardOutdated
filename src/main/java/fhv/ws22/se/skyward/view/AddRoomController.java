package fhv.ws22.se.skyward.view;

import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.Room;
import fhv.ws22.se.skyward.view.util.NotificationUtil;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.hibernate.annotations.Check;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import java.util.*;

public class AddRoomController extends AbstractController {
    @FXML
    private TableView<RoomDto> roomTable;
    @FXML
    private TableColumn<RoomDto, Integer> roomNumberCol;
    @FXML
    private TableColumn<RoomDto, CheckBox> checkboxCol;
    @FXML
    private TableColumn<RoomDto, String> roomTypeNameCol;
    @FXML
    private TableColumn<RoomDto, String> roomStateNameCol;

    @FXML
    private CheckBox filterSingleRoom;
    @FXML
    private CheckBox filterDoubleRoom;
    @FXML
    private CheckBox filterTripleRoom;
    @FXML
    private CheckBox filterTwinRoom;
    @FXML
    private CheckBox filterQueenRoom;

    @FXML
    public Label bNrPlaceholder;

    @FXML
    protected void initialize() {
        tmpBooking = session.getTmpBooking();
        List<RoomDto> selectedRooms = new ArrayList<>();

        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeNameCol.setCellValueFactory(new PropertyValueFactory<>("roomTypeName"));
        roomStateNameCol.setCellValueFactory(new PropertyValueFactory<>("roomStateName"));
        checkboxCol.setCellValueFactory(entry -> {
            CheckBox checkBox = new CheckBox();

            for (RoomDto room : tmpBooking.getRooms()) {
                if (room.getId() == entry.getValue().getId()) {
                    checkBox.setSelected(true);
                }
            }

            checkBox.setOnAction(event -> {
                selectedRooms.add(entry.getValue());
                tmpBooking.setRooms(selectedRooms);
            });
            return new SimpleObjectProperty<>(checkBox);
        });

        /*roomTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                List<RoomDto> selectedRooms = roomTable.getSelectionModel().getSelectedItems();
                tmpBooking.setRooms(selectedRooms);
            }
        });*/
        configureListener();
        updateData();
    }

    private void configureListener() {
        if (session.getRoomFilterMap().size() == 0) {
            HashMap<String, Boolean> filterMap = new HashMap<String, Boolean>();
            filterMap.put("Single", false);
            filterMap.put("Double", false);
            filterMap.put("Triple", false);
            filterMap.put("Twin", false);
            filterMap.put("Queen", false);
            session.setRoomFilterMap(filterMap);
        }
        HashMap<String, Boolean> filterMap = session.getRoomFilterMap();

        filterSingleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Single", filterSingleRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterDoubleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Double", filterDoubleRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterTripleRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Triple", filterTripleRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterTwinRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Twin", filterTwinRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
        filterQueenRoom.selectedProperty().addListener((observable, oldValue, newValue) -> {
            filterMap.put("Queen", filterQueenRoom.isSelected());
            session.setRoomFilterMap(filterMap);
            updateData();
        });
    }

    @FXML
    public void onConfirmButtonClick(ActionEvent event) {
        session.update(tmpBooking.getId(), tmpBooking);
        NotificationUtil.getInstance().showSuccessNotification("The Rooms were added to the booking", event);
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    @FXML
    public void backButtonClick(ActionEvent event) {
        controllerNavigationUtil.navigate(event, "src/main/resources/fhv/ws22/se/skyward/bookings.fxml", "Booking");
    }

    public void updateData() {
        HashMap<String, Boolean> filterMap = session.getRoomFilterMap();
        if (filterMap.get("Single")) {
            filterSingleRoom.setSelected(true);
        }
        if (filterMap.get("Double")) {
            filterDoubleRoom.setSelected(true);
        }
        if (filterMap.get("Triple")) {
            filterTripleRoom.setSelected(true);
        }
        if (filterMap.get("Twin")) {
            filterTwinRoom.setSelected(true);
        }
        if (filterMap.get("Queen")) {
            filterQueenRoom.setSelected(true);
        }

        roomTable.getItems().clear();

        List<RoomDto> rooms = session.getAvailableRooms(tmpBooking.getCheckInDateTime(), tmpBooking.getCheckOutDateTime());

        List<RoomDto> check = checkboxCol.getTableView().getItems();

        if (rooms != null) {
            roomTable.getItems().addAll(rooms);
        }
        bNrPlaceholder.setText(tmpBooking.getBookingNumber().toString());
    }
}
