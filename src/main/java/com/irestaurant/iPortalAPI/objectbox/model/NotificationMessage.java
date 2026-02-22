package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import com.irestaurant.iPortalAPI.enumerators.MessageStatuses;
import com.irestaurant.iPortalAPI.enumerators.Usecases;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationMessage {

  @Id(assignable = true)
  long id;
  String title;
  String message;

  @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
  LocalDateTime creationDate = LocalDateTime.now();
  int usecase = Usecases.None.ordinal();
  int messageStatus = MessageStatuses.NotSent.ordinal();

  String branchId;

  private transient ToOne<Notification> notification;
  private transient ToOne<User> sender;

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

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public int getUsecase() {
    return usecase;
  }

  public void setUsecase(int usecase) {
    this.usecase = usecase;
  }

  public int getMessageStatus() {
    return messageStatus;
  }

  public void setMessageStatus(int messageStatus) {
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

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 71 * hash + Objects.hashCode(this.title);
    hash = 71 * hash + Objects.hashCode(this.message);
    hash = 71 * hash + Objects.hashCode(this.creationDate);
    hash = 71 * hash + this.usecase;
    hash = 71 * hash + this.messageStatus;
    hash = 71 * hash + Objects.hashCode(this.branchId);
    hash = 71 * hash + Objects.hashCode(this.notification);
    hash = 71 * hash + Objects.hashCode(this.sender);
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
    final NotificationMessage other = (NotificationMessage) obj;
    if (this.id != other.id) {
      return false;
    }
    if (this.usecase != other.usecase) {
      return false;
    }
    if (this.messageStatus != other.messageStatus) {
      return false;
    }
    if (!Objects.equals(this.title, other.title)) {
      return false;
    }
    if (!Objects.equals(this.message, other.message)) {
      return false;
    }
    if (!Objects.equals(this.branchId, other.branchId)) {
      return false;
    }
    if (!Objects.equals(this.creationDate, other.creationDate)) {
      return false;
    }
    if (!Objects.equals(this.notification, other.notification)) {
      return false;
    }
    return Objects.equals(this.sender, other.sender);
  }

  @Override
  public String toString() {
    return "NotificationMessage{" + "id=" + id + ", title=" + title + ", message=" + message + ", creationDate="
        + creationDate + ", usecase=" + usecase + ", messageStatus=" + messageStatus + ", branchId=" + branchId
        + ", notification=" + notification + ", sender=" + sender + '}';
  }

}
