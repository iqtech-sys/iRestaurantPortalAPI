package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Setting {

  @Id(assignable = true)
  long id;
  String key;
  String value;
  @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
  LocalDateTime createdDate = LocalDateTime.now();

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

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 41 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 41 * hash + Objects.hashCode(this.key);
    hash = 41 * hash + Objects.hashCode(this.value);
    hash = 41 * hash + Objects.hashCode(this.createdDate);
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
    final Setting other = (Setting) obj;
    if (this.id != other.id) {
      return false;
    }
    if (!Objects.equals(this.key, other.key)) {
      return false;
    }
    if (!Objects.equals(this.value, other.value)) {
      return false;
    }
    return Objects.equals(this.createdDate, other.createdDate);
  }

  @Override
  public String toString() {
    return "Setting{" + "id=" + id + ", key=" + key + ", value=" + value + ", createdDate=" + createdDate + '}';
  }

}
