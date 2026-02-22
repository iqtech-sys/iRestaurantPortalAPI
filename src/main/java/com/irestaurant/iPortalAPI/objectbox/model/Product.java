package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;

@Entity
public class Product {

    @Id(assignable = true)
    long id;
    @Unique(onConflict = ConflictStrategy.REPLACE)
    String title;
    String description;
    String branchId;

    double price;
    double rating;
    byte[] image;
    boolean isAvailable;
    double calories;
    int preparationTime;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime createdDate;

    private transient ToOne<Category> category;
    private transient ToMany<OrderItem> orderItems;// OrderItems
    private transient ToMany<Kitchen> kitchens;// Kitchens

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

    //
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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
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

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
