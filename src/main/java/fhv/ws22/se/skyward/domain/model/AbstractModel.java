package fhv.ws22.se.skyward.domain.model;

import fhv.ws22.se.skyward.domain.dtos.AbstractDto;
import fhv.ws22.se.skyward.domain.dtos.BookingDto;
import fhv.ws22.se.skyward.domain.dtos.CustomerDto;
import fhv.ws22.se.skyward.domain.dtos.RoomDto;
import fhv.ws22.se.skyward.persistence.entity.*;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractModel {
    protected ModelMapper modelMapper;
    private UUID id;
    private static Map<Class, Class> entityModelClassMap;

    public AbstractModel() {
        modelMapper = new ModelMapper();
    }

    static {
        entityModelClassMap = new HashMap<Class, Class>();
        entityModelClassMap.put(Customer.class, CustomerModel.class);
        entityModelClassMap.put(Room.class, RoomModel.class);
        entityModelClassMap.put(Booking.class, BookingModel.class);
        entityModelClassMap.put(Invoice.class, InvoiceModel.class);
        entityModelClassMap.put(Address.class, AddressModel.class);
        entityModelClassMap.put(ChargeableItem.class, ChargeableItemModel.class);
    }

    public static Class<? extends AbstractEntity> getEntityClass(Class<? extends AbstractModel> modelClass) {
        return entityModelClassMap.get(modelClass);
    }

    public abstract <T extends AbstractDto> T toDto();
    public abstract <T extends AbstractEntity> T toEntity();
    public static <T extends AbstractModel> T toModel(AbstractEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        Class<T> modelClazz = entityModelClassMap.get(entity.getClass());
        return modelMapper.map(entity, modelClazz);
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
}
