package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import com.irestaurant.iPortalAPI.enumerators.LogEvents;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class LogEvent {

  @Id(assignable = true)
  long id;

  int logEvent = LogEvents.None.ordinal();

  @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
  LocalDateTime createdDate = LocalDateTime.now();

  String branchId;

  List<String> args;

  private transient ToOne<User> user;

  public ToOne<User> getUser() {
    return user;
  }

  public void setUser(ToOne<User> user) {
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getLogEvent() {
    return logEvent;
  }

  public void setLogEvent(int logEvent) {
    this.logEvent = logEvent;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  public List<String> getArgs() {
    return args;
  }

  public void setArgs(List<String> args) {
    this.args = args;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 17 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 17 * hash + this.logEvent;
    hash = 17 * hash + Objects.hashCode(this.createdDate);
    hash = 17 * hash + Objects.hashCode(this.branchId);
    hash = 17 * hash + Objects.hashCode(this.args);
    hash = 17 * hash + Objects.hashCode(this.user);
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
    final LogEvent other = (LogEvent) obj;
    if (this.id != other.id) {
      return false;
    }
    if (this.logEvent != other.logEvent) {
      return false;
    }
    if (!Objects.equals(this.branchId, other.branchId)) {
      return false;
    }
    if (!Objects.equals(this.createdDate, other.createdDate)) {
      return false;
    }
    if (!Objects.equals(this.args, other.args)) {
      return false;
    }
    return Objects.equals(this.user, other.user);
  }

  @Override
  public String toString() {
    return "LogEvent{" + "id=" + id + ", logEvent=" + logEvent + ", createdDate=" + createdDate + ", branchId="
        + branchId + ", args=" + args + ", user=" + user + '}';
  }
}
