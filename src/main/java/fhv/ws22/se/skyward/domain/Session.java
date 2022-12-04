package fhv.ws22.se.skyward.domain;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import fhv.ws22.se.skyward.domain.dtos.*;
import fhv.ws22.se.skyward.domain.model.*;
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
        filterMap = new HashMap<>();
        dtoModelClassMap = new HashMap<>();
        dtoModelClassMap.put(CustomerDto.class, CustomerModel.class);
        dtoModelClassMap.put(RoomDto.class, RoomModel.class);
        dtoModelClassMap.put(BookingDto.class, BookingModel.class);
        dtoModelClassMap.put(InvoiceDto.class, InvoiceModel.class);
        dtoModelClassMap.put(AddressDto.class, AddressModel.class);
        dtoModelClassMap.put(ChargeableItemDto.class, ChargeableItemModel.class);
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDto> List getAll(Class<T> clazz) {
        List<? extends AbstractModel> modelList = dataService.getAll(dtoModelClassMap.get(clazz));
        List<T> dtoList = new ArrayList<T>();
        for (AbstractModel model : modelList) {
            dtoList.add((T) model.toDto());
        }
        return dtoList;
    }

    public <T extends AbstractDto> T get(UUID id, Class<T> clazz) {
        return dataService.get(id, dtoModelClassMap.get(clazz)).toDto();
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

        List<RoomModel> allRooms = (List<RoomModel>) dataService.getAll(RoomModel.class);
        List<RoomDto> availableRooms = new ArrayList<RoomDto>();
        for (RoomModel model : allRooms) {
            availableRooms.add(model.toDto());
        }

        List<BookingModel> allBookings = (List<BookingModel>) dataService.getAll(BookingModel.class);
        // check if any booking is in the same time frame to remove it from the available rooms
        for (BookingModel booking : allBookings) {
            if (booking.getCheckInDateTime() != null && booking.getCheckOutDateTime() != null) {
                if (booking.getCheckInDateTime().toLocalDate().isBefore(checkOut.toLocalDate()) && booking.getCheckOutDateTime().toLocalDate().isAfter(checkIn.toLocalDate())) {
                    List<RoomModel> blockedRooms = booking.getRooms();
                    if (blockedRooms != null) {
                        for (RoomModel room : blockedRooms) {
                            availableRooms.removeIf(roomDto -> roomDto.getId().equals(room.getId()));
                        }
                    }
                }
            }
        }

        return availableRooms;
    }

    public List<RoomDto> filterRooms(List<RoomDto> rooms, HashMap<String, Boolean> filterMap) {
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




    public BookingDto getTmpBooking() {
        if (tmpBookingId == null) {
            BookingDto booking = new BookingDto();
            booking.setCheckInDateTime(LocalDateTime.now());
            booking.setIsCheckedIn(false);
            tmpBookingId = addAndReturnId(BookingDto.class, booking);
        }
        return get(tmpBookingId, BookingDto.class);
    }
    public void resetTmpBooking() {
        tmpBookingId = null;
    }
    public void setTmpBooking(BookingDto booking) {
        BookingDto tmpBid = get(booking.getId(), BookingDto.class);
        if (tmpBid == null) {
            throw new IllegalArgumentException("Booking could not be added");
        }
        tmpBookingId = tmpBid.getId();
    }

    public InvoiceDto getTmpInvoice() {
        if (tmpInvoiceId == null) {
            BookingDto booking = getTmpBooking();

            List<InvoiceDto> invoices = getAll(InvoiceDto.class);
            invoices.removeIf(invoice -> invoice.getBooking().getId() != booking.getId());

            if (invoices.isEmpty()) {
                AddressDto customerAddress = new AddressDto("MainStreet", 43, 1234, "Vienna", "Austria");
                add(customerAddress);
                InvoiceDto invoice = new InvoiceDto(LocalDateTime.now(), false, customerAddress, booking);
                invoice.setBilledCustomer(booking.getCustomers().get(0));
                tmpInvoiceId = addAndReturnId(InvoiceDto.class, invoice);

                List<ChargeableItemDto> chargeableItemDtos = new ArrayList<>();
                for (RoomDto room : booking.getRooms()) {
                    Integer quantity = (int) Duration.between(booking.getCheckInDateTime(), booking.getCheckOutDateTime()).toDays() + 1;
                    BigDecimal price = getPrice(room.getRoomTypeName());
                    ChargeableItemDto chargeableItem = new ChargeableItemDto(room.getRoomTypeName() + " Room: " + room.getRoomNumber(), price, quantity, booking);
                    chargeableItemDtos.add(chargeableItem);
                    add(chargeableItem);
                }
            } else {
                tmpInvoiceId = invoices.get(0).getId();
            }
        }

        return get(tmpInvoiceId, InvoiceDto.class);
    }

    private BigDecimal getPrice(String roomTypeName) {
        switch (roomTypeName) {
            case "Single":
                return new BigDecimal(100);
            case "Double":
                return new BigDecimal(150);
            case "Triple":
                return new BigDecimal(200);
            case "Twin":
                return new BigDecimal(150);
            case "Queen":
                return new BigDecimal(300);
            default:
                return new BigDecimal(0);
        }
    }

    public void resetTmpInvoice() {
        tmpInvoiceId = null;
    }
    public void setTmpInvoice(InvoiceDto invoice) {
        InvoiceDto tmpIid = get(invoice.getId(), InvoiceDto.class);
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
