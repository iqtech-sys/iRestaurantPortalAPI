package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import com.irestaurant.iPortalAPI.enumerators.NotificationTypes;
import com.irestaurant.iPortalAPI.enumerators.Usecases;
import io.objectbox.annotation.Sync;
import java.util.Date;

@Sync
@Entity
public class Notification {

    @Id(assignable = true)
    long id;

    String title;
    String subtitle;
    long badgeCount;
    long type = NotificationTypes.None.ordinal();
    long usecase = Usecases.None.ordinal();
    boolean canReply;

    //@Convert(converter = DateConverter.class, dbType = Long.class)
    Date creationDate;

    String branchId;

    public ToOne<User> user;

    @Backlink(to = "notification")
    private ToMany<NotificationMessage> messages;

    public Notification() {
       this.user = new ToOne<>(this, Notification_.user);
       this.messages = new ToMany<>(this, Notification_.messages);
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public long getBadgeCount() {
        return badgeCount;
    }

    public void setBadgeCount(long badgeCount) {
        this.badgeCount = badgeCount;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getUsecase() {
        return usecase;
    }

    public void setUsecase(long usecase) {
        this.usecase = usecase;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }

    public ToMany<NotificationMessage> getMessages() {
        return messages;
    }

    public void setMessages(ToMany<NotificationMessage> messages) {
        this.messages = messages;
    }
    
    
}
