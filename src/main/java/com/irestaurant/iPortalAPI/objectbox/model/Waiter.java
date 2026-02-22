package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Waiter {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String name;

    String phoneNumber;

    int totalOrders;

    byte[] image;

    boolean isActive;

    String branchId;

    private transient ToMany<Kitchen> kitchens;

    private transient ToMany<OrderEntry> orderEntries;

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

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    public void setKitchens(ToMany<Kitchen> kitchens) {
        this.kitchens = kitchens;
    }

    public void setOrderEntries(ToMany<OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    public ToMany<Kitchen> getKitchens() {
        return kitchens;
    }

    public ToMany<OrderEntry> getOrderEntries() {
        return orderEntries;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.phoneNumber);
        hash = 79 * hash + this.totalOrders;
        hash = 79 * hash + Arrays.hashCode(this.image);
        hash = 79 * hash + (this.isActive ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.branchId);
        hash = 79 * hash + Objects.hashCode(this.kitchens);
        hash = 79 * hash + Objects.hashCode(this.orderEntries);
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
        final Waiter other = (Waiter) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.totalOrders != other.totalOrders) {
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
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Arrays.equals(this.image, other.image)) {
            return false;
        }
        if (!Objects.equals(this.kitchens, other.kitchens)) {
            return false;
        }
        return Objects.equals(this.orderEntries, other.orderEntries);
    }

    @Override
    public String toString() {
        return "Waiter{" + "id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", totalOrders="
                + totalOrders + ", image=" + image + ", isActive=" + isActive + ", branchId=" + branchId + ", kitchens="
                + kitchens + ", orderEntries=" + orderEntries + '}';
    }
}
