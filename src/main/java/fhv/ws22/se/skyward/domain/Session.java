package fhv.ws22.se.skyward.domain;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fhv.ws22.se.skyward.domain.dtos.*;
import fhv.ws22.se.skyward.domain.model.*;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.view.SessionService;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Singleton
public class Session implements SessionService {
    @Inject
    private DataService dataService;
    private UUID tmpBookingId;
    private UUID tmpInvoiceId;
    private HashMap<String, Boolean> filterMap;
    private Map<Class, Class> dtoModelClassMap;


    public Session() {
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
        List<? extends AbstractModel> modelList = dataService.getAll(dtoModelClassMap.get(clazz));
        List<T> dtoList = new ArrayList<T>();
        for (AbstractModel model : modelList) {
            dtoList.add((T) model.toDto());
        }
        return dtoList;
    }

    public <T extends AbstractDto> void add(T t) {
        AbstractModel model = t.toModel();
        dataService.add(model);
    }

    private <T extends AbstractDto> UUID addAndReturnId(Class<T> clazz, T t) {
        AbstractModel model = t.toModel();
        return dataService.addAndReturnId(dtoModelClassMap.get(clazz), model);
    }

    public <T extends AbstractDto> void update(UUID id, T t) {
        AbstractModel model = t.toModel();
        dataService.update(id, model);
    }

    public <T extends AbstractDto> void delete(UUID id, Class<T> clazz) {
        dataService.delete(id, dtoModelClassMap.get(clazz));
    }

    public List<RoomDto> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn == null || checkOut == null) {
            return null;
        }
        List<RoomModel> modelRooms = (List<RoomModel>) dataService.getAll(RoomModel.class);
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

        List<BookingModel> modelBookings = (List<BookingModel>) dataService.getAll(BookingModel.class);
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




    public BookingDto getTmpBooking() {
        if (tmpBookingId == null) {
            BookingModel booking = new BookingModel();
            booking.setCheckInDateTime(LocalDateTime.now());
            booking.setIsCheckedIn(false);
            tmpBookingId = addAndReturnId(BookingDto.class, booking.toDto());
        }
        BookingModel booking = dataService.get(tmpBookingId, BookingModel.class);

        return booking.toDto();
    }
    public void resetTmpBooking() {
        tmpBookingId = null;
    }
    public void setTmpBooking(BookingDto booking) {
        BookingModel tmpBid = dataService.get(booking.getId(), BookingModel.class);
        if (tmpBid == null) {
            throw new IllegalArgumentException("Booking could not be added");
        }
        tmpBookingId = tmpBid.getId();
    }

    public InvoiceDto getTmpInvoice() {
        if (tmpInvoiceId == null) {
            BookingDto booking = getTmpBooking();
            if (booking.getInvoices() == null || booking.getInvoices().isEmpty()) {
                AddressModel customerAddress = new AddressModel("MainStreet", "43", "1234", "Vienna", "Austria");
                dataService.add(customerAddress);
                InvoiceModel invoice = new InvoiceModel(LocalDateTime.now(), false, customerAddress, booking.toModel());
                tmpInvoiceId = addAndReturnId(InvoiceDto.class, invoice.toDto());

                List<ChargeableItemDto> chargeableItemModels = new ArrayList<>();
                for (RoomDto room : booking.getRooms()) {
                    Integer quantity = (int) Duration.between(booking.getCheckInDateTime(), booking.getCheckOutDateTime()).toDays() + 1;
                    ChargeableItemModel chargeableItem = new ChargeableItemModel(room.getRoomTypeName(), new BigDecimal(100), quantity, booking.toModel());
                    chargeableItemModels.add(chargeableItem.toDto());
                    dataService.add(chargeableItem);
                }

                booking.setChargeableItems(chargeableItemModels);
                dataService.update(booking.getId(), booking.toModel());
            } else {
                tmpInvoiceId = booking.getInvoices().get(0).getId();
            }
        }
        InvoiceModel invoice = dataService.get(tmpInvoiceId, InvoiceModel.class);

        return invoice.toDto();
    }
    public void resetTmpInvoice() {
        tmpInvoiceId = null;
    }
    public void setTmpInvoice(InvoiceDto invoice) {
        InvoiceModel tmpIid = dataService.get(invoice.getId(), InvoiceModel.class);
        if (tmpIid == null) {
            throw new IllegalArgumentException("Invoice could not be added");
        }
        tmpInvoiceId = tmpIid.getId();
    }



    public void setRoomFilterMap(HashMap<String, Boolean> filterMap) {
        this.filterMap = filterMap;
    }
    public HashMap<String, Boolean> getRoomFilterMap() {
        return filterMap;
    }
}
