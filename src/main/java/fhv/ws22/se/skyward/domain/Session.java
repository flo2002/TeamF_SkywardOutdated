package fhv.ws22.se.skyward.domain;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.domain.model.AbstractModel;
import fhv.ws22.se.skyward.domain.model.BookingModel;
import fhv.ws22.se.skyward.domain.model.CustomerModel;
import fhv.ws22.se.skyward.persistence.DatabaseFacade;
import fhv.ws22.se.skyward.persistence.broker.BookingBroker;
import fhv.ws22.se.skyward.persistence.broker.BrokerBase;
import fhv.ws22.se.skyward.persistence.broker.CustomerBroker;
import fhv.ws22.se.skyward.persistence.broker.RoomBroker;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Session {
    private final DatabaseFacade dbf;
    private UUID tmpBookingId;

    public Session() {
        dbf = DatabaseFacade.getInstance();
    }


    public <T extends AbstractDto> List getAll(Class<T> clazz) {
        return dbf.getAll(clazz);
    }
    public <T extends AbstractDto> void add(T t) {
        dbf.add(t);
    }
    public <T extends AbstractDto> void update(UUID id, T t) {
        dbf.update(id, t);
    };
    public <T extends AbstractDto> void delete(UUID id, Class<T> clazz) {
        dbf.delete(id, clazz);
    };
    private <T extends AbstractDto> UUID addAndReturnId(Class<T> clazz, T t) {
        return dbf.addAndReturnId(clazz, t);
    }

    public BookingDto getTmpBooking() {
        if (tmpBookingId == null) {
            BookingDto booking = new BookingDto();
            booking.setCheckInDateTime(LocalDateTime.now());

            tmpBookingId = addAndReturnId(BookingDto.class, booking);
        }

        return (BookingDto) dbf.get(tmpBookingId, BookingDto.class);
    }
}
