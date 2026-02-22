package com.irestaurant.iPortalAPI.converter;

import io.objectbox.converter.PropertyConverter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class LocalDateTimeConverter implements PropertyConverter<LocalDateTime, Long> {

    @Override
    public LocalDateTime convertToEntityProperty(Long databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        return LocalDateTime.ofEpochSecond(databaseValue, 0, ZoneOffset.UTC);
    }

    @Override
    public Long convertToDatabaseValue(LocalDateTime entityProperty) {
        if (entityProperty == null) {
            return null;
        }
        return entityProperty.toEpochSecond(ZoneOffset.UTC);
    }
}
