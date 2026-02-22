package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Kitchen {

  @Id(assignable = true)
  long id;

  @Unique(onConflict = ConflictStrategy.REPLACE)
  String title;

  String description;

  byte[] image;

  String branchId;

  private transient ToMany<Waiter> waiters;
  private transient ToMany<Delivery> deliveries;
  private transient ToMany<Product> products;

  public ToMany<Waiter> getWaiters() {
    return waiters;
  }

  public void setWaiters(ToMany<Waiter> waiters) {
    this.waiters = waiters;
  }

  public ToMany<Delivery> getDeliveries() {
    return deliveries;
  }

  public void setDeliveries(ToMany<Delivery> deliveries) {
    this.deliveries = deliveries;
  }

  public ToMany<Product> getProducts() {
    return products;
  }

  public void setProducts(ToMany<Product> products) {
    this.products = products;
  }

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
    hash = 89 * hash + Objects.hashCode(this.title);
    hash = 89 * hash + Objects.hashCode(this.description);
    hash = 89 * hash + Arrays.hashCode(this.image);
    hash = 89 * hash + Objects.hashCode(this.branchId);
    hash = 89 * hash + Objects.hashCode(this.waiters);
    hash = 89 * hash + Objects.hashCode(this.deliveries);
    hash = 89 * hash + Objects.hashCode(this.products);
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
    final Kitchen other = (Kitchen) obj;
    if (this.id != other.id) {
      return false;
    }
    if (!Objects.equals(this.title, other.title)) {
      return false;
    }
    if (!Objects.equals(this.description, other.description)) {
      return false;
    }
    if (!Objects.equals(this.branchId, other.branchId)) {
      return false;
    }
    if (!Arrays.equals(this.image, other.image)) {
      return false;
    }
    if (!Objects.equals(this.waiters, other.waiters)) {
      return false;
    }
    if (!Objects.equals(this.deliveries, other.deliveries)) {
      return false;
    }
    return Objects.equals(this.products, other.products);
  }

  @Override
  public String toString() {
    return "Kitchen{" + "id=" + id + ", title=" + title + ", description=" + description + ", image=" + image
        + ", branchId=" + branchId + ", waiters=" + waiters + ", deliveries=" + deliveries + ", products=" + products
        + '}';
  }
}
