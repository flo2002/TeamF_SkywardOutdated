package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.model.AbstractModel;
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
    private Map<Class, Class> dtoModelClassMap;


    public Session() {
        dbf = DatabaseFacade.getInstance();
        filterMap = new HashMap<String, Boolean>();
        dtoModelClassMap = new HashMap<Class, Class>();
        dtoModelClassMap.put(CustomerDto.class, CustomerModel.class);
        dtoModelClassMap.put(RoomDto.class, RoomModel.class);
        dtoModelClassMap.put(BookingDto.class, BookingModel.class);
    }


    public <T extends AbstractDto> List getAll(Class<T> clazz) {
        List<? extends AbstractModel> modelList = dbf.getAll(dtoModelClassMap.get(clazz));
        List<T> dtoList = new ArrayList<T>();
        for (AbstractModel model : modelList) {
            dtoList.add((T) model.toDto());
        }
        return dtoList;
    }

    public <T extends AbstractDto> void add(T t) {
        AbstractModel model = t.toModel();
        dbf.add(model);
    }

    private <T extends AbstractDto> UUID addAndReturnId(Class<T> clazz, T t) {
        AbstractModel model = t.toModel();
        return dbf.addAndReturnId(dtoModelClassMap.get(clazz), model);
    }

    public <T extends AbstractDto> void update(UUID id, T t) {
        AbstractModel model = t.toModel();
        dbf.update(id, model);
    }

    public <T extends AbstractDto> void delete(UUID id, Class<T> clazz) {
        dbf.delete(id, dtoModelClassMap.get(clazz));
    }

    public List<RoomDto> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null) {
            return null;
        }
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

        List<BookingModel> modelBookings = dbf.getAll(BookingModel.class);
        List<RoomDto> availableRoomsUpdated = new ArrayList<RoomDto>();
        for (BookingModel booking : modelBookings) {
            // check if booking is not in the time frame of the new booking - not sure if this is right:
            if (booking.getCheckInDateTime().isAfter(checkOut) || booking.getCheckOutDateTime().isBefore(checkIn)) {
                if (!booking.getCheckInDateTime().equals(checkIn) && !booking.getCheckOutDateTime().equals(checkOut)) {
                    for (RoomDto room : availableRooms) {
                        availableRoomsUpdated.add(room);
                    }
                }
            }
        }

        return availableRoomsUpdated;
    }

    public void resetTmpBooking() {
        tmpBookingId = null;
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
