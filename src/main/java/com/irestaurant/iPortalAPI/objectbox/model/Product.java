package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.Sync;
import java.time.LocalDateTime;
import java.util.Date;

@Sync
@Entity
public class Product {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String title;

    double price;
    double rating;
    byte[] image;
    String description;
    double calories;
    long preparationTime;
    boolean isAvailable;
    String branchId;

    //@Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    Date createdDate = new Date();

    public ToOne<Category> category;

    @Backlink(to = "product")
    private ToMany<OrderItem> orderItems;

    private ToMany<Kitchen> kitchens;

    public Product() {
        this.category = new ToOne<>(this, Product_.category);
        //
        this.orderItems = new ToMany<>(this, Product_.orderItems);
        this.kitchens = new ToMany<>(this, Product_.kitchens);
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(long preparationTime) {
        this.preparationTime = preparationTime;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ToOne<Category> getCategory() {
        return category;
    }

    public void setCategory(ToOne<Category> category) {
        this.category = category;
    }

    public ToMany<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ToMany<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ToMany<Kitchen> getKitchens() {
        return kitchens;
    }

    public void setKitchens(ToMany<Kitchen> kitchens) {
        this.kitchens = kitchens;
    }
    
    
}
