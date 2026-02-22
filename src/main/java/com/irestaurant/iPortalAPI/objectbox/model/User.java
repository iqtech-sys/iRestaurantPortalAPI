package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.enumerators.UserTypes;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class User {
    
  @Id(assignable = true)
  long id;
  
  @Unique(onConflict = ConflictStrategy.REPLACE)
  String email;
  
  String userName;
  
  String password;
  
  String privileges;
  
  int userType = UserTypes.Cashier.ordinal();
  
  boolean isActive = true;
  
  byte[] image;
  
  String branchId;

  //@Backlink('user') // Links back from order to user.
  ToMany<Order> orders;
  //@Backlink('user') // Links back from notification to user.
  ToMany<Notification> notifications;
  //@Backlink('user') // Links back from order to user.
  ToMany<LogEvent> logs;
  //@Backlink('sender') // Links back from notification message to sender user.
  ToMany<NotificationMessage> notificationMessages;// = ToMany<NotificationMessage>();

  ToOne<Waiter> waiter;
  ToOne<Kitchen> kitchen;
  ToOne<Delivery> delivery;

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

  public int getUserType() {
    return userType;
  }

  public void setUserType(int userType) {
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.userName);
        hash = 53 * hash + Objects.hashCode(this.password);
        hash = 53 * hash + Objects.hashCode(this.privileges);
        hash = 53 * hash + this.userType;
        hash = 53 * hash + (this.isActive ? 1 : 0);
        hash = 53 * hash + Arrays.hashCode(this.image);
        hash = 53 * hash + Objects.hashCode(this.branchId);
        hash = 53 * hash + Objects.hashCode(this.orders);
        hash = 53 * hash + Objects.hashCode(this.notifications);
        hash = 53 * hash + Objects.hashCode(this.logs);
        hash = 53 * hash + Objects.hashCode(this.notificationMessages);
        hash = 53 * hash + Objects.hashCode(this.waiter);
        hash = 53 * hash + Objects.hashCode(this.kitchen);
        hash = 53 * hash + Objects.hashCode(this.delivery);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.userType != other.userType) {
            return false;
        }
        if (this.isActive != other.isActive) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.privileges, other.privileges)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Arrays.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.orders, other.orders)) {
            return false;
        }
        if (!Objects.equals(this.notifications, other.notifications)) {
            return false;
        }
        if (!Objects.equals(this.logs, other.logs)) {
            return false;
        }
        if (!Objects.equals(this.notificationMessages, other.notificationMessages)) {
            return false;
        }
        if (!Objects.equals(this.waiter, other.waiter)) {
            return false;
        }
        if (!Objects.equals(this.kitchen, other.kitchen)) {
            return false;
        }
        return Objects.equals(this.delivery, other.delivery);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", email=" + email + ", userName=" + userName + ", password=" + password + ", privileges=" + privileges + ", userType=" + userType + ", isActive=" + isActive + ", image=" + image + ", branchId=" + branchId + ", orders=" + orders + ", notifications=" + notifications + ", logs=" + logs + ", notificationMessages=" + notificationMessages + ", waiter=" + waiter + ", kitchen=" + kitchen + ", delivery=" + delivery + '}';
    }
  
  
}
