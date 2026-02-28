package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import com.irestaurant.iPortalAPI.enumerators.UserTypes;
import io.objectbox.annotation.Sync;

@Sync
@Entity
public class User {

    @Id(assignable = true)
    public long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    public String email;

    public String userName;
    public String password;
    public String privileges;

    public long userType = UserTypes.Cashier.ordinal();
    public boolean isActive = true;
    public byte[] image;
    public String branchId;

    @Backlink(to = "user")
    public ToMany<Order> orders;

    @Backlink(to = "user")
    public ToMany<Notification> notifications;

    @Backlink(to = "user")
    public ToMany<LogEvent> logs;

    @Backlink(to = "sender")
    public ToMany<NotificationMessage> notificationMessages;

    public ToOne<Waiter> waiter;
    public ToOne<Kitchen> kitchen;
    public ToOne<Delivery> delivery;

    public User() {
        this.orders = new ToMany<>(this, User_.orders);
        this.notifications = new ToMany<>(this, User_.notifications);
        this.logs = new ToMany<>(this, User_.logs);
        this.notificationMessages = new ToMany<>(this, User_.notificationMessages);
        //
        this.waiter = new ToOne<>(this, User_.waiter);
        this.kitchen = new ToOne<>(this, User_.kitchen);
        this.delivery = new ToOne<>(this, User_.delivery);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public long getUserType() {
        return userType;
    }

    public void setUserType(long userType) {
        this.userType = userType;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToMany<Order> getOrders() {
        return orders;
    }

    public void setOrders(ToMany<Order> orders) {
        this.orders = orders;
    }

    public ToMany<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ToMany<Notification> notifications) {
        this.notifications = notifications;
    }

    public ToMany<LogEvent> getLogs() {
        return logs;
    }

    public void setLogs(ToMany<LogEvent> logs) {
        this.logs = logs;
    }

    public ToMany<NotificationMessage> getNotificationMessages() {
        return notificationMessages;
    }

    public void setNotificationMessages(ToMany<NotificationMessage> notificationMessages) {
        this.notificationMessages = notificationMessages;
    }

    public ToOne<Waiter> getWaiter() {
        return waiter;
    }

    public void setWaiter(ToOne<Waiter> waiter) {
        this.waiter = waiter;
    }

    public ToOne<Kitchen> getKitchen() {
        return kitchen;
    }

    public void setKitchen(ToOne<Kitchen> kitchen) {
        this.kitchen = kitchen;
    }

    public ToOne<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(ToOne<Delivery> delivery) {
        this.delivery = delivery;
    }
    
    
}