package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import com.irestaurant.iPortalAPI.enumerators.DeliveryBys;
import io.objectbox.annotation.Sync;

@Sync
@Entity
public class Delivery {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String name;

    String phoneNumber;
    byte[] image;
    String location;
    long deliveryBy = DeliveryBys.Car.ordinal();
    long priority;
    boolean isActive;
    String branchId;

    private ToMany<Order> orders;
    private ToMany<Kitchen> kitchens;
    private ToMany<OrderEntry> orderEntries;

    public Delivery() {
        this.orders = new ToMany<>(this, Delivery_.orders);
        this.kitchens = new ToMany<>(this, Delivery_.kitchens);
        this.orderEntries = new ToMany<>(this, Delivery_.orderEntries);
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

    public long getDeliveryBy() {
        return deliveryBy;
    }

    public void setDeliveryBy(long deliveryBy) {
        this.deliveryBy = deliveryBy;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
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

    public ToMany<Order> getOrders() {
        return orders;
    }

    public void setOrders(ToMany<Order> orders) {
        this.orders = orders;
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