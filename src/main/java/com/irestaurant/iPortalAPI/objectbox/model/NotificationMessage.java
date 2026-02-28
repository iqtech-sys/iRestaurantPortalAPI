package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToOne;
import com.irestaurant.iPortalAPI.enumerators.Usecases;
import com.irestaurant.iPortalAPI.enumerators.MessageStatuses;
import io.objectbox.annotation.Sync;
import java.util.Date;
import java.util.Calendar;

@Sync
@Entity
public class NotificationMessage {

    @Id(assignable = true)
    long id;

    String title;
    String message;

    //@Convert(converter = DateConverter.class, dbType = Long.class)
    Date creationDate = Calendar.getInstance().getTime();

    long usecase = Usecases.None.ordinal();
    long messageStatus = MessageStatuses.NotSent.ordinal();

    String branchId;

    public ToOne<Notification> notification;
    public ToOne<User> sender;

    public NotificationMessage() {
      this.notification = new ToOne<>(this, NotificationMessage_.notification);
      this.sender = new ToOne<>(this, NotificationMessage_.sender);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public long getUsecase() {
        return usecase;
    }

    public void setUsecase(long usecase) {
        this.usecase = usecase;
    }

    public long getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(long messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToOne<Notification> getNotification() {
        return notification;
    }

    public void setNotification(ToOne<Notification> notification) {
        this.notification = notification;
    }

    public ToOne<User> getSender() {
        return sender;
    }

    public void setSender(ToOne<User> sender) {
        this.sender = sender;
    }
    
    
}