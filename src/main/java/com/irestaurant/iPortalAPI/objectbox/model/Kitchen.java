package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.annotation.Unique;
import io.objectbox.converter.PropertyConverter;
import io.objectbox.relation.ToMany;
import java.util.Date;

@Sync
@Entity
public class Kitchen {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String title;

    String description;
    byte[] image;
    String branchId;

    private ToMany<Waiter> waiters;
    private ToMany<Delivery> deliveries;
    private ToMany<Product> products;
    
    public Kitchen() {
        this.waiters = new ToMany<>(this, Kitchen_.waiters);
        this.deliveries = new ToMany<>(this, Kitchen_.deliveries);
        this.products = new ToMany<>(this, Kitchen_.products);
    }

//    public static class DateConverter implements PropertyConverter<Date, Long> {
//        @Override
//        public Long convertToDatabaseValue(Date entityProperty) {
//            return entityProperty != null ? entityProperty.getTime() : null;
//        }
//
//        @Override
//        public Date convertToEntityProperty(Long databaseValue) {
//            return databaseValue != null ? new Date(databaseValue) : null;
//        }
//    }

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
    
    
}
