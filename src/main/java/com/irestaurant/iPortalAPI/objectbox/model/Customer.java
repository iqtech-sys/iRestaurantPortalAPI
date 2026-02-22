package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Customer {

  @Id(assignable = true)
  long id;
  @Unique(onConflict = ConflictStrategy.REPLACE)
  String name;
  String phoneNumber;
  String address;
  int totalOrders;
  double amountSpent;
  byte[] image;
  String location;
  double location_lat;
  double location_lon;
  String branchId;

  // @Backlink('customer') // Links back from order to customer.
  private transient ToMany<Order> orders;

  public ToMany<Order> getOrders() {
    return orders;
  }

  public void setOrders(ToMany<Order> orders) {
    this.orders = orders;
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getTotalOrders() {
    return totalOrders;
  }

  public void setTotalOrders(int totalOrders) {
    this.totalOrders = totalOrders;
  }

  public double getAmountSpent() {
    return amountSpent;
  }

  public void setAmountSpent(double amountSpent) {
    this.amountSpent = amountSpent;
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

  public double getLocation_lat() {
    return location_lat;
  }

  public void setLocation_lat(double location_lat) {
    this.location_lat = location_lat;
  }

  public double getLocation_lon() {
    return location_lon;
  }

  public void setLocation_lon(double location_lon) {
    this.location_lon = location_lon;
  }

  public String getBranchId() {
    return branchId;
  }

  public void setBranchId(String branchId) {
    this.branchId = branchId;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 71 * hash + Objects.hashCode(this.name);
    hash = 71 * hash + Objects.hashCode(this.phoneNumber);
    hash = 71 * hash + Objects.hashCode(this.address);
    hash = 71 * hash + this.totalOrders;
    hash = 71 * hash
        + (int) (Double.doubleToLongBits(this.amountSpent) ^ (Double.doubleToLongBits(this.amountSpent) >>> 32));
    hash = 71 * hash + Arrays.hashCode(this.image);
    hash = 71 * hash + Objects.hashCode(this.location);
    hash = 71 * hash
        + (int) (Double.doubleToLongBits(this.location_lat) ^ (Double.doubleToLongBits(this.location_lat) >>> 32));
    hash = 71 * hash
        + (int) (Double.doubleToLongBits(this.location_lon) ^ (Double.doubleToLongBits(this.location_lon) >>> 32));
    hash = 71 * hash + Objects.hashCode(this.branchId);
    hash = 71 * hash + Objects.hashCode(this.orders);
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
    final Customer other = (Customer) obj;
    if (this.id != other.id) {
      return false;
    }
    if (this.totalOrders != other.totalOrders) {
      return false;
    }
    if (Double.doubleToLongBits(this.amountSpent) != Double.doubleToLongBits(other.amountSpent)) {
      return false;
    }
    if (Double.doubleToLongBits(this.location_lat) != Double.doubleToLongBits(other.location_lat)) {
      return false;
    }
    if (Double.doubleToLongBits(this.location_lon) != Double.doubleToLongBits(other.location_lon)) {
      return false;
    }
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.phoneNumber, other.phoneNumber)) {
      return false;
    }
    if (!Objects.equals(this.address, other.address)) {
      return false;
    }
    if (!Objects.equals(this.location, other.location)) {
      return false;
    }
    if (!Objects.equals(this.branchId, other.branchId)) {
      return false;
    }
    if (!Arrays.equals(this.image, other.image)) {
      return false;
    }
    return Objects.equals(this.orders, other.orders);
  }

  @Override
  public String toString() {
    return "Customer{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", address=" + address
        + ", totalOrders=" + totalOrders + ", amountSpent=" + amountSpent + ", image=" + image + ", location="
        + location + ", location_lat=" + location_lat + ", location_lon=" + location_lon + ", branchId=" + branchId
        + ", order=" + orders + '}';
  }

}