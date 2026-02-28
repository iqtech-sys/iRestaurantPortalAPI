package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToOne;
import com.irestaurant.iPortalAPI.enumerators.LogEvents;
import io.objectbox.annotation.Sync;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Sync
@Entity
public class LogEvent {

    @Id(assignable = true)
    long id;

    long logEvent = LogEvents.None.ordinal();

    //@Convert(converter = DateConverter.class, dbType = Long.class)
    Date createdDate;

    String branchId;

    //@Convert(converter = StringListConverter.class, dbType = String.class)
    String[] args;

    public ToOne<User> user;

    public LogEvent() {
       this.user = new ToOne<>(this, LogEvent_.user);
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

//    public static class StringListConverter implements PropertyConverter<List<String>, String> {
//        @Override
//        public String convertToDatabaseValue(List<String> entityProperty) {
//            if (entityProperty == null || entityProperty.isEmpty()) {
//                return null;
//            }
//            return String.join("|||", entityProperty);
//        }
//
//        @Override
//        public List<String> convertToEntityProperty(String databaseValue) {
//            if (databaseValue == null || databaseValue.isEmpty()) {
//                return null;
//            }
//            return Arrays.asList(databaseValue.split("\\|\\|\\|"));
//        }
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLogEvent() {
        return logEvent;
    }

    public void setLogEvent(long logEvent) {
        this.logEvent = logEvent;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }
    
    
}
