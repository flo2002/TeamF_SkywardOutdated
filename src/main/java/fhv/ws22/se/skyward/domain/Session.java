package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.*;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;

import java.time.LocalDateTime;
import java.util.*;

public class Session {
    private final DatabaseFacade dbf;
    private UUID tmpBookingId;
    private UUID tmpInvoiceId;
    private HashMap<String, Boolean> filterMap;
    private Map<Class, Class> dtoModelClassMap;


    public Session() {
        dbf = DatabaseFacade.getInstance();
        filterMap = new HashMap<String, Boolean>();
        dtoModelClassMap = new HashMap<Class, Class>();
        dtoModelClassMap.put(CustomerDto.class, CustomerModel.class);
        dtoModelClassMap.put(RoomDto.class, RoomModel.class);
        dtoModelClassMap.put(BookingDto.class, BookingModel.class);
        dtoModelClassMap.put(InvoiceDto.class, InvoiceModel.class);
        dtoModelClassMap.put(AddressDto.class, AddressModel.class);
        dtoModelClassMap.put(ChargeableItemDto.class, ChargeableItemModel.class);
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
        // check if any booking is in the same time frame to remove it from the available rooms
        for (BookingModel booking : modelBookings) {
            if (booking.getCheckInDateTime().isBefore(checkOut) || booking.getCheckOutDateTime().isAfter(checkIn)) {
                List<RoomModel> blockedRooms = booking.getRooms();
                if (blockedRooms != null) {
                    for (RoomModel room : blockedRooms) {
                        availableRooms.remove(room.toDto());
                    }
                }
            }
        }

        return availableRooms;
    }




    public BookingDto getTmpBooking() throws BookingDateNotValidException {
        if (tmpBookingId == null) {
            BookingModel booking = new BookingModel();
            booking.setCheckInDateTime(LocalDateTime.now());
            booking.setIsCheckedIn(false);
            tmpBookingId = addAndReturnId(BookingDto.class, booking.toDto());
        }
        BookingModel booking = dbf.get(tmpBookingId, BookingModel.class);

        return booking.toDto();
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

    public InvoiceDto getTmpInvoice() throws BookingDateNotValidException {
        if (tmpInvoiceId == null) {
            if (getTmpBooking().getInvoices() == null || getTmpBooking().getInvoices().isEmpty()) {
                AddressModel customerAddress = null;
                try {
                    customerAddress = new AddressModel("MainStreet", "43", "1234", "Vienna", "Austria");
                } catch (AddressNotValidException e) {e.printStackTrace();}
                dbf.add(customerAddress);
                InvoiceModel invoice = new InvoiceModel(LocalDateTime.now(), false, customerAddress, getTmpBooking().toModel());
                tmpInvoiceId = addAndReturnId(InvoiceDto.class, invoice.toDto());
            } else {
                tmpInvoiceId = getTmpBooking().getInvoices().get(0).getId();
            }
        }
        InvoiceModel invoice = dbf.get(tmpInvoiceId, InvoiceModel.class);

        return invoice.toDto();
    }
    public void resetTmpInvoice() {
        tmpInvoiceId = null;
    }
    public void setTmpInvoice(InvoiceDto invoice) {
        InvoiceModel tmpIid = dbf.get(invoice.getId(), InvoiceModel.class);
        if (tmpIid == null) {
            throw new IllegalArgumentException("Invoice could not be added");
        }
        tmpInvoiceId = tmpIid.getId();
    }



    public void setFilterMap(HashMap<String, Boolean> filterMap) {
        this.filterMap = filterMap;
    }
    public HashMap<String, Boolean> getFilterMap() {
        return filterMap;
    }
}
