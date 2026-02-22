package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import com.irestaurant.iPortalAPI.enumerators.MqttPaymentMethods;
import com.irestaurant.iPortalAPI.enumerators.OrderStatuses;
import com.irestaurant.iPortalAPI.enumerators.OrderTypes;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Order {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String orderNumber;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime createdDate;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime modifiedDate;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime inPreparationDate;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime readyDate;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime deliveredDate;

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime cancelledDate;

    int orderType = OrderTypes.DineIn.ordinal();// OrderTypes enum.
    int orderStatus = OrderStatuses.Pending.ordinal();// OrderStatuses enum.
    int paymentMethod = MqttPaymentMethods.Cash.ordinal();// Payment method enum.

    int preparationTime = 0;

    String notes;
    String doneNotes;
    String stopNote;
    String branchId;

    ToOne<Customer> customer;
    ToOne<User> user;
    ToOne<Table> table;
    ToOne<Delivery> delivery;

    ToMany<OrderItem> orderItems;
    ToMany<OrderEntry> orderEntries;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LocalDateTime getInPreparationDate() {
        return inPreparationDate;
    }

    public void setInPreparationDate(LocalDateTime inPreparationDate) {
        this.inPreparationDate = inPreparationDate;
    }

    public LocalDateTime getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(LocalDateTime readyDate) {
        this.readyDate = readyDate;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public LocalDateTime getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(LocalDateTime cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDoneNotes() {
        return doneNotes;
    }

    public void setDoneNotes(String doneNotes) {
        this.doneNotes = doneNotes;
    }

    public String getStopNote() {
        return stopNote;
    }

    public void setStopNote(String stopNote) {
        this.stopNote = stopNote;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToOne<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(ToOne<Customer> customer) {
        this.customer = customer;
    }

    public ToOne<User> getUser() {
        return user;
    }

    public void setUser(ToOne<User> user) {
        this.user = user;
    }

    public ToOne<Table> getTable() {
        return table;
    }

    public void setTable(ToOne<Table> table) {
        this.table = table;
    }

    public ToOne<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(ToOne<Delivery> delivery) {
        this.delivery = delivery;
    }

    public ToMany<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ToMany<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ToMany<OrderEntry> getOrderEntries() {
        return orderEntries;
    }

    public void setOrderEntries(ToMany<OrderEntry> orderEntries) {
        this.orderEntries = orderEntries;
    }

    public Order() {
    }

    public Order(long id, String orderNumber, LocalDateTime createdDate, LocalDateTime modifiedDate,
            LocalDateTime inPreparationDate, LocalDateTime readyDate,
            LocalDateTime deliveredDate, LocalDateTime cancelledDate, String notes, String doneNotes, String stopNote,
            String branchId,
            ToOne<Customer> customer, ToOne<User> user, ToOne<Table> table, ToOne<Delivery> delivery,
            ToMany<OrderItem> orderItems,
            ToMany<OrderEntry> orderEntries) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.inPreparationDate = inPreparationDate;
        this.readyDate = readyDate;
        this.deliveredDate = deliveredDate;
        this.cancelledDate = cancelledDate;
        this.notes = notes;
        this.doneNotes = doneNotes;
        this.stopNote = stopNote;
        this.branchId = branchId;
        this.customer = customer;
        this.user = user;
        this.table = table;
        this.delivery = delivery;
        this.orderItems = orderItems;
        this.orderEntries = orderEntries;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.orderNumber);
        hash = 83 * hash + Objects.hashCode(this.createdDate);
        hash = 83 * hash + Objects.hashCode(this.modifiedDate);
        hash = 83 * hash + Objects.hashCode(this.inPreparationDate);
        hash = 83 * hash + Objects.hashCode(this.readyDate);
        hash = 83 * hash + Objects.hashCode(this.deliveredDate);
        hash = 83 * hash + Objects.hashCode(this.cancelledDate);
        hash = 83 * hash + this.orderType;
        hash = 83 * hash + this.orderStatus;
        hash = 83 * hash + this.paymentMethod;
        hash = 83 * hash + this.preparationTime;
        hash = 83 * hash + Objects.hashCode(this.notes);
        hash = 83 * hash + Objects.hashCode(this.doneNotes);
        hash = 83 * hash + Objects.hashCode(this.stopNote);
        hash = 83 * hash + Objects.hashCode(this.branchId);
        hash = 83 * hash + Objects.hashCode(this.customer);
        hash = 83 * hash + Objects.hashCode(this.user);
        hash = 83 * hash + Objects.hashCode(this.table);
        hash = 83 * hash + Objects.hashCode(this.delivery);
        hash = 83 * hash + Objects.hashCode(this.orderItems);
        hash = 83 * hash + Objects.hashCode(this.orderEntries);
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
        final Order other = (Order) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.orderType != other.orderType) {
            return false;
        }
        if (this.orderStatus != other.orderStatus) {
            return false;
        }
        if (this.paymentMethod != other.paymentMethod) {
            return false;
        }
        if (this.preparationTime != other.preparationTime) {
            return false;
        }
        if (!Objects.equals(this.orderNumber, other.orderNumber)) {
            return false;
        }
        if (!Objects.equals(this.notes, other.notes)) {
            return false;
        }
        if (!Objects.equals(this.doneNotes, other.doneNotes)) {
            return false;
        }
        if (!Objects.equals(this.stopNote, other.stopNote)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.createdDate, other.createdDate)) {
            return false;
        }
        if (!Objects.equals(this.modifiedDate, other.modifiedDate)) {
            return false;
        }
        if (!Objects.equals(this.inPreparationDate, other.inPreparationDate)) {
            return false;
        }
        if (!Objects.equals(this.readyDate, other.readyDate)) {
            return false;
        }
        if (!Objects.equals(this.deliveredDate, other.deliveredDate)) {
            return false;
        }
        if (!Objects.equals(this.cancelledDate, other.cancelledDate)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.table, other.table)) {
            return false;
        }
        if (!Objects.equals(this.delivery, other.delivery)) {
            return false;
        }
        if (!Objects.equals(this.orderItems, other.orderItems)) {
            return false;
        }
        return Objects.equals(this.orderEntries, other.orderEntries);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", orderNumber=" + orderNumber + ", createdDate=" + createdDate
                + ", modifiedDate=" + modifiedDate + ", inPreparationDate=" + inPreparationDate + ", readyDate="
                + readyDate + ", deliveredDate=" + deliveredDate + ", cancelledDate=" + cancelledDate + ", orderType="
                + orderType + ", orderStatus=" + orderStatus + ", paymentMethod=" + paymentMethod + ", preparationTime="
                + preparationTime + ", notes=" + notes + ", doneNotes=" + doneNotes + ", stopNote=" + stopNote
                + ", branchId=" + branchId + ", customer=" + customer + ", user=" + user + ", table=" + table
                + ", delivery=" + delivery + ", orderItems=" + orderItems + ", orderEntries=" + orderEntries + '}';
    }
}
