package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.domain.model.RoomModel;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;

import java.time.LocalDateTime;
import java.util.*;

public class Session {
    private final DatabaseFacade dbf;
    private UUID tmpBookingId;
    private HashMap<String, Boolean> filterMap;
    private Map<Class, Class> models;


    public Session() {
        dbf = DatabaseFacade.getInstance();
        filterMap = new HashMap<String, Boolean>();
        models = new HashMap<Class, Class>();
        models.put(CustomerDto.class, CustomerModel.class);
        models.put(RoomDto.class, RoomModel.class);
        models.put(BookingDto.class, BookingModel.class);
    }


    public <T extends AbstractDto> List getAll(Class<T> clazz) {
        if (clazz == BookingDto.class) {
            List<BookingModel> modelList = dbf.getAll(models.get(clazz));
            List<BookingDto> dtoList = new ArrayList<BookingDto>();
            for (BookingModel model : modelList) {
                dtoList.add(model.toDto());
            }
            return dtoList;
        }
        if (clazz == CustomerDto.class) {
            List<CustomerModel> modelList = dbf.getAll(models.get(clazz));
            List<CustomerDto> dtoList = new ArrayList<CustomerDto>();
            for (CustomerModel model : modelList) {
                dtoList.add(model.toDto());
            }
            return dtoList;
        }
        throw new IllegalArgumentException("Class not supported");
    }

    public <T extends AbstractDto> void add(T t) {
        if (t instanceof BookingDto) {
            BookingModel model = ((BookingDto) t).toModel();
            dbf.add(model);
        } else {
            throw new IllegalArgumentException("Class not supported");
        }
    }

    private <T extends AbstractDto> UUID addAndReturnId(Class<T> clazz, T t) {
        if (t instanceof BookingDto) {
            BookingModel model = ((BookingDto) t).toModel();
            return dbf.addAndReturnId(BookingModel.class, model);
        } else {
            throw new IllegalArgumentException("Class not supported");
        }
    }

    public <T extends AbstractDto> void update(UUID id, T t) {
        if (t instanceof BookingDto) {
            BookingModel model = ((BookingDto) t).toModel();
            dbf.update(id, model);
        } else {
            throw new IllegalArgumentException("Class not supported");
        }
    }

    public <T extends AbstractDto> void delete(UUID id, Class<T> clazz) {
        dbf.delete(id, models.get(clazz));
    }

    public List<RoomDto> getAvailableRooms() {
        List<RoomModel> modelRooms = dbf.getAll(RoomModel.class);
        List<RoomDto> rooms = new ArrayList<RoomDto>();
        for (RoomModel model : modelRooms) {
            rooms.add(model.toDto());
        }

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


    public void setTmpBooking(BookingDto booking) {
        BookingModel tmpBid = dbf.get(booking.getId(), BookingModel.class);
        if (tmpBid == null) {
            throw new IllegalArgumentException("Booking could not be added");
        }
        tmpBookingId = tmpBid.getId();
    }
    public BookingDto getTmpBooking() {
        if (tmpBookingId == null) {
            BookingModel booking = new BookingModel();
            booking.setCheckInDateTime(LocalDateTime.now());
            tmpBookingId = addAndReturnId(BookingDto.class, booking.toDto());
        }
        BookingModel booking = dbf.get(tmpBookingId, BookingModel.class);

        return booking.toDto();
    }

    public void setFilterMap(HashMap<String, Boolean> filterMap) {
        this.filterMap = filterMap;
    }
    public HashMap<String, Boolean> getFilterMap() {
        return filterMap;
    }
}
