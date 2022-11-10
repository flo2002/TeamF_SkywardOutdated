package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import javafx.scene.control.CheckBox;

import java.time.LocalDateTime;
import java.util.*;

public class Session {
    private final DatabaseFacade dbf;
    private UUID tmpBookingId;
    private HashMap<String, Boolean> filterMap;

    private static Session singleton;

    public static synchronized Session getInstance() {
        if (singleton == null) {
            singleton = new Session();
        }
        return singleton;
    }

    public Session() {
        dbf = DatabaseFacade.getInstance();
    }


    public <T extends AbstractDto> List getAll(Class<T> clazz) {
        return dbf.getAll(clazz);
    }
    public <T extends AbstractDto> void add(T t) {
        if (t instanceof BookingDto) {
            BookingModel bm = BookingModel.toModel((BookingDto) t);
            BookingDto bd = bm.toDto();
            dbf.add(bd);
        } else {
            dbf.add(t);
        }
    }
    public <T extends AbstractDto> void update(UUID id, T t) {
        if (t instanceof BookingDto) {
            BookingModel bm = BookingModel.toModel((BookingDto) t);
            BookingDto bd = bm.toDto();
            dbf.update(id, bd);
        } else {
            dbf.update(id, t);
        }
    };
    public <T extends AbstractDto> void delete(UUID id, Class<T> clazz) {
        dbf.delete(id, clazz);
    };
    private <T extends AbstractDto> UUID addAndReturnId(Class<T> clazz, T t) {
        if (t instanceof BookingDto) {
            BookingModel bm = BookingModel.toModel((BookingDto) t);
            BookingDto bd = bm.toDto();
            return dbf.addAndReturnId(BookingDto.class, bd);
        } else {
            return dbf.addAndReturnId(clazz, t);
        }
    }

    public BookingDto getTmpBooking() {
        if (tmpBookingId == null) {
            BookingDto booking = new BookingDto();
            booking.setCheckInDateTime(LocalDateTime.now());

            tmpBookingId = addAndReturnId(BookingDto.class, booking);
        }

        return dbf.get(tmpBookingId, BookingDto.class);
    }

    public void setFilterMap(HashMap<String, Boolean> filterMap) {
        this.filterMap = filterMap;
    }
    public HashMap<String, Boolean> getFilterMap() {
        return filterMap;
    }

    public List<RoomDto> getAvailableRooms(Class<RoomDto> roomDtoClass) {
        List<RoomDto> rooms = dbf.getAll(roomDtoClass);

        List<RoomDto> availableRooms = new ArrayList<RoomDto>();
        for (RoomDto room : rooms) {
            if (room.getRoomTypeName().equals("Single") && filterMap.get("Single")) {
                availableRooms.add(room);
            } else if (room.getRoomTypeName().equals("Double") && filterMap.get("Double")) {
                availableRooms.add(room);
            } else if (room.getRoomTypeName().equals("Triple") && filterMap.get("Triple")) {
                availableRooms.add(room);
            } else if (room.getRoomTypeName().equals("Twin") && filterMap.get("Twin")) {
                availableRooms.add(room);
            } else if (room.getRoomTypeName().equals("Queen") && filterMap.get("Queen")) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }
}
