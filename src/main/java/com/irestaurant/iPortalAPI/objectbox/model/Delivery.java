package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.enumerators.DeliveryBys;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Delivery {

  @Id(assignable = true)
  long id;

  @Unique(onConflict = ConflictStrategy.REPLACE)
  String name;

  String phoneNumber;

  byte[] image;

  String location;

  int deliveryBy = DeliveryBys.Car.ordinal();

  int priority;

  boolean isActive;

  String branchId;

  private transient ToMany<Order> orders;// transient: unserializable
  private transient ToMany<Kitchen> kitchens;
  private transient ToMany<OrderEntry> orderEntries;

  public ToMany<Order> getOrders() {
    return orders;
  }

  public ToMany<Kitchen> getKitchens() {
    return kitchens;
  }

  public ToMany<OrderEntry> getOrderEntries() {
    return orderEntries;
  }

  public void setOrders(ToMany<Order> orders) {
    this.orders = orders;
  }

  public void setKitchens(ToMany<Kitchen> kitchens) {
    this.kitchens = kitchens;
  }

  public void setOrderEntries(ToMany<OrderEntry> orderEntries) {
    this.orderEntries = orderEntries;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public int getDeliveryBy() {
    return deliveryBy;
  }

  public void setDeliveryBy(int deliveryBy) {
    this.deliveryBy = deliveryBy;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public boolean isIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 83 * hash + Objects.hashCode(this.name);
    hash = 83 * hash + Objects.hashCode(this.phoneNumber);
    hash = 83 * hash + Arrays.hashCode(this.image);
    hash = 83 * hash + Objects.hashCode(this.location);
    hash = 83 * hash + this.deliveryBy;
    hash = 83 * hash + this.priority;
    hash = 83 * hash + (this.isActive ? 1 : 0);
    hash = 83 * hash + Objects.hashCode(this.branchId);
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
    final Delivery other = (Delivery) obj;
    if (this.id != other.id) {
      return false;
    }
    if (this.deliveryBy != other.deliveryBy) {
      return false;
    }
    if (this.priority != other.priority) {
      return false;
    }
    if (this.isActive != other.isActive) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
      return false;
    }
    if (!Objects.equals(this.location, other.location)) {
      return false;
    }
    if (!Objects.equals(this.branchId, other.branchId)) {
      return false;
    }
    return Arrays.equals(this.image, other.image);
  }

  @Override
  public String toString() {
    return "Delivery{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", image=" + image
        + ", location=" + location + ", deliveryBy=" + deliveryBy + ", priority=" + priority + ", isActive=" + isActive
        + ", branchId=" + branchId + '}';
  }

}
