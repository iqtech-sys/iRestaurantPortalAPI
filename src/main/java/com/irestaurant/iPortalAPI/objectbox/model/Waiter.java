package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;

@Sync
@Entity
public class Waiter {

    @Id(assignable = true)
    public long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    public String name;

    public String phoneNumber;
    public long totalOrders;
    public byte[] image;
    public boolean isActive;
    public String branchId;

    public ToMany<Kitchen> kitchens;
    public ToMany<OrderEntry> orderEntries;

    public Waiter() {
        this.kitchens = new ToMany<>(this, Waiter_.kitchens);
        this.orderEntries = new ToMany<>(this, Waiter_.orderEntries);
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

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
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

    public ToMany<Kitchen> getKitchens() {
        return kitchens;
    }

    public void setKitchens(ToMany<Kitchen> kitchens) {
        this.kitchens = kitchens;
    }

    public ToMany<OrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(ToMany<OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }
    
    
}