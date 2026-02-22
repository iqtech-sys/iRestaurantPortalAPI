package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class OrderItem {

    @Id(assignable = true)
    long id;
    int quantity;
    double discount;
    String notes;
    boolean isDone;
    String branchId;

    private transient ToOne<Product> product;
    private transient ToOne<Order> order;

    // **** Snapshot properties ****
    int snapshot_id = 0;
    String snapshot_title;
    double snapshot_price = 0;
    String snapshot_currency;
    double snapshot_discount = 0;
    double snapshot_taxRate = 0;
    int snapshot_quantity = 0;
    double snapshot_rating = 0;
    byte[] snapshot_image;
    String snapshot_description;
    double snapshot_calories = 0;
    int snapshot_preparationTime = 0;
    boolean snapshot_isAvailable = false;
    String snapshot_notes;
    String snapshot_branchId;
    boolean snapshot_isDone = false;
    // *****************************

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 89 * hash + this.quantity;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.discount) ^ (Double.doubleToLongBits(this.discount) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.notes);
        hash = 89 * hash + (this.isDone ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.branchId);
        hash = 89 * hash + Objects.hashCode(this.product);
        hash = 89 * hash + Objects.hashCode(this.order);
        hash = 89 * hash + this.snapshot_id;
        hash = 89 * hash + Objects.hashCode(this.snapshot_title);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.snapshot_price) ^ (Double.doubleToLongBits(this.snapshot_price) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.snapshot_currency);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.snapshot_discount) ^ (Double.doubleToLongBits(this.snapshot_discount) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.snapshot_taxRate) ^ (Double.doubleToLongBits(this.snapshot_taxRate) >>> 32));
        hash = 89 * hash + this.snapshot_quantity;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.snapshot_rating) ^ (Double.doubleToLongBits(this.snapshot_rating) >>> 32));
        hash = 89 * hash + Arrays.hashCode(this.snapshot_image);
        hash = 89 * hash + Objects.hashCode(this.snapshot_description);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.snapshot_calories) ^ (Double.doubleToLongBits(this.snapshot_calories) >>> 32));
        hash = 89 * hash + this.snapshot_preparationTime;
        hash = 89 * hash + (this.snapshot_isAvailable ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.snapshot_notes);
        hash = 89 * hash + Objects.hashCode(this.snapshot_branchId);
        hash = 89 * hash + (this.snapshot_isDone ? 1 : 0);
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
        final OrderItem other = (OrderItem) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.discount) != Double.doubleToLongBits(other.discount)) {
            return false;
        }
        if (this.isDone != other.isDone) {
            return false;
        }
        if (this.snapshot_id != other.snapshot_id) {
            return false;
        }
        if (Double.doubleToLongBits(this.snapshot_price) != Double.doubleToLongBits(other.snapshot_price)) {
            return false;
        }
        if (Double.doubleToLongBits(this.snapshot_discount) != Double.doubleToLongBits(other.snapshot_discount)) {
            return false;
        }
        if (Double.doubleToLongBits(this.snapshot_taxRate) != Double.doubleToLongBits(other.snapshot_taxRate)) {
            return false;
        }
        if (this.snapshot_quantity != other.snapshot_quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.snapshot_rating) != Double.doubleToLongBits(other.snapshot_rating)) {
            return false;
        }
        if (Double.doubleToLongBits(this.snapshot_calories) != Double.doubleToLongBits(other.snapshot_calories)) {
            return false;
        }
        if (this.snapshot_preparationTime != other.snapshot_preparationTime) {
            return false;
        }
        if (this.snapshot_isAvailable != other.snapshot_isAvailable) {
            return false;
        }
        if (this.snapshot_isDone != other.snapshot_isDone) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.snapshot_title, other.snapshot_title)) {
            return false;
        }
        if (!Objects.equals(this.snapshot_currency, other.snapshot_currency)) {
            return false;
        }
        if (!Objects.equals(this.snapshot_description, other.snapshot_description)) {
            return false;
        }
        if (!Objects.equals(this.snapshot_notes, other.snapshot_notes)) {
            return false;
        }
        if (!Objects.equals(this.snapshot_branchId, other.snapshot_branchId)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        return Arrays.equals(this.snapshot_image, other.snapshot_image);
    }

    @Override
    public String toString() {
        return "OrderItem{" + "id=" + id + ", quantity=" + quantity + ", discount=" + discount + ", notes=" + notes
                + ", isDone=" + isDone + ", branchId=" + branchId + ", product=" + product + ", order=" + order
                + ", snapshot_id=" + snapshot_id + ", snapshot_title=" + snapshot_title + ", snapshot_price="
                + snapshot_price + ", snapshot_currency=" + snapshot_currency + ", snapshot_discount="
                + snapshot_discount + ", snapshot_taxRate=" + snapshot_taxRate + ", snapshot_quantity="
                + snapshot_quantity + ", snapshot_rating=" + snapshot_rating + ", snapshot_image=" + snapshot_image
                + ", snapshot_description=" + snapshot_description + ", snapshot_calories=" + snapshot_calories
                + ", snapshot_preparationTime=" + snapshot_preparationTime + ", snapshot_isAvailable="
                + snapshot_isAvailable + ", snapshot_notes=" + snapshot_notes + ", snapshot_branchId="
                + snapshot_branchId + ", snapshot_isDone=" + snapshot_isDone + '}';
    }
}
