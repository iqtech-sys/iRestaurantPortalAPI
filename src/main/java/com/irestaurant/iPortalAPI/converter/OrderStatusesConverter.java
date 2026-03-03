package com.irestaurant.iPortalAPI.converter;

import com.irestaurant.iPortalAPI.enumerators.OrderStatuses;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Component
@Converter(autoApply = true)
public class OrderStatusesConverter implements AttributeConverter<OrderStatuses, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatuses attribute) {
        return (attribute == null) ? null : attribute.ordinal();
    }

    @Override
    public OrderStatuses convertToEntityAttribute(Integer code) {
        if (code == null || code < 0 || code >= OrderStatuses.values().length) {
            return null;
        }
        return OrderStatuses.values()[code];
    }

}
