package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.relation.ToOne;

@Sync
@Entity
public class OrderItem {

    @Id(assignable = true)
    long id;

    long quantity;
    double discount;
    String notes;
    boolean isDone;
    String branchId;

    public ToOne<Product> product;
    public ToOne<Order> order;

    //**** Snapshot properties ****
    long snapshot_id = 0;
    String snapshot_title = "";
    double snapshot_price = 0;
    String snapshot_currency = "";
    double snapshot_discount = 0;
    double snapshot_taxRate = 0;
    long snapshot_quantity = 0;
    double snapshot_rating = 0;
    byte[] snapshot_image;
    String snapshot_description = "";
    double snapshot_calories = 0;
    long snapshot_preparationTime = 0;
    boolean snapshot_isAvailable = false;
    String snapshot_notes = "";
    String snapshot_branchId = "";
    boolean snapshot_isDone = false;
    //*****************************

    public OrderItem() {
        this.product = new ToOne<>(this, OrderItem_.product);
        this.order = new ToOne<>(this, OrderItem_.order);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isIsDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToOne<Product> getProduct() {
        return product;
    }

    public void setProduct(ToOne<Product> product) {
        this.product = product;
    }

    public ToOne<Order> getOrder() {
        return order;
    }

    public void setOrder(ToOne<Order> order) {
        this.order = order;
    }

    public long getSnapshot_id() {
        return snapshot_id;
    }

    public void setSnapshot_id(long snapshot_id) {
        this.snapshot_id = snapshot_id;
    }

    public String getSnapshot_title() {
        return snapshot_title;
    }

    public void setSnapshot_title(String snapshot_title) {
        this.snapshot_title = snapshot_title;
    }

    public double getSnapshot_price() {
        return snapshot_price;
    }

    public void setSnapshot_price(double snapshot_price) {
        this.snapshot_price = snapshot_price;
    }

    public String getSnapshot_currency() {
        return snapshot_currency;
    }

    public void setSnapshot_currency(String snapshot_currency) {
        this.snapshot_currency = snapshot_currency;
    }

    public double getSnapshot_discount() {
        return snapshot_discount;
    }

    public void setSnapshot_discount(double snapshot_discount) {
        this.snapshot_discount = snapshot_discount;
    }

    public double getSnapshot_taxRate() {
        return snapshot_taxRate;
    }

    public void setSnapshot_taxRate(double snapshot_taxRate) {
        this.snapshot_taxRate = snapshot_taxRate;
    }

    public long getSnapshot_quantity() {
        return snapshot_quantity;
    }

    public void setSnapshot_quantity(long snapshot_quantity) {
        this.snapshot_quantity = snapshot_quantity;
    }

    public double getSnapshot_rating() {
        return snapshot_rating;
    }

    public void setSnapshot_rating(double snapshot_rating) {
        this.snapshot_rating = snapshot_rating;
    }

    public byte[] getSnapshot_image() {
        return snapshot_image;
    }

    public void setSnapshot_image(byte[] snapshot_image) {
        this.snapshot_image = snapshot_image;
    }

    public String getSnapshot_description() {
        return snapshot_description;
    }

    public void setSnapshot_description(String snapshot_description) {
        this.snapshot_description = snapshot_description;
    }

    public double getSnapshot_calories() {
        return snapshot_calories;
    }

    public void setSnapshot_calories(double snapshot_calories) {
        this.snapshot_calories = snapshot_calories;
    }

    public long getSnapshot_preparationTime() {
        return snapshot_preparationTime;
    }

    public void setSnapshot_preparationTime(long snapshot_preparationTime) {
        this.snapshot_preparationTime = snapshot_preparationTime;
    }

    public boolean isSnapshot_isAvailable() {
        return snapshot_isAvailable;
    }

    public void setSnapshot_isAvailable(boolean snapshot_isAvailable) {
        this.snapshot_isAvailable = snapshot_isAvailable;
    }

    public String getSnapshot_notes() {
        return snapshot_notes;
    }

    public void setSnapshot_notes(String snapshot_notes) {
        this.snapshot_notes = snapshot_notes;
    }

    public String getSnapshot_branchId() {
        return snapshot_branchId;
    }

    public void setSnapshot_branchId(String snapshot_branchId) {
        this.snapshot_branchId = snapshot_branchId;
    }

    public boolean isSnapshot_isDone() {
        return snapshot_isDone;
    }

    public void setSnapshot_isDone(boolean snapshot_isDone) {
        this.snapshot_isDone = snapshot_isDone;
    }
    
    
}
