package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.converter.PropertyConverter;

import java.util.Date;

@Entity
public class Setting {

    @Id(assignable = true)
    public long id;

    public String key;
    public String value;

    //@Convert(converter = DateConverter.class, dbType = Long.class)
    public Date createdDate;

    public Setting() {
    }

//    public static class DateConverter implements PropertyConverter<Date, Long> {
//        @Override
//        public Long convertToDatabaseValue(Date entityProperty) {
//            return entityProperty != null ? entityProperty.getTime() : null;
//        }
//
//        @Override
//        public Date convertToEntityProperty(Long databaseValue) {
//            return databaseValue != null ? new Date(databaseValue) : null;
//        }
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    
    
}