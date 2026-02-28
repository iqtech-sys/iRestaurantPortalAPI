package com.irestaurant.iPortalAPI.objectbox.model;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.ConflictStrategy;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;
import com.irestaurant.iPortalAPI.enumerators.OrderTypes;
import com.irestaurant.iPortalAPI.enumerators.OrderStatuses;
import com.irestaurant.iPortalAPI.enumerators.MqttPaymentMethods;
import io.objectbox.annotation.Sync;
import java.util.Date;
import java.util.List;

@Sync
@Entity
public class Order {

    @Id(assignable = true)
    long id;

    @Unique(onConflict = ConflictStrategy.REPLACE)
    String orderNumber;

    // @Convert(converter = DateConverter.class, dbType = Long.class)
    Date createdDate;

    // @Convert(converter = DateConverter.class, dbType = Long.class)
    Date modifiedDate;

    // @Convert(converter = DateConverter.class, dbType = Long.class)
    Date inPreparationDate;

    // @Convert(converter = DateConverter.class, dbType = Long.class)
    Date readyDate;

    // @Convert(converter = DateConverter.class, dbType = Long.class)
    Date deliveredDate;

    // @Convert(converter = DateConverter.class, dbType = Long.class)
    Date cancelledDate;

    long orderType = OrderTypes.DineIn.ordinal();
    long orderStatus = OrderStatuses.Pending.ordinal();
    long paymentMethod = MqttPaymentMethods.Cash.ordinal();

    long preparationTime = 0;
    String notes = "";
    String doneNotes = "";
    String stopNote = "";
    String branchId = "";

    public ToOne<Customer> customer;
    public ToOne<User> user;
    public ToOne<Table> table;
    public ToOne<Delivery> delivery;

    @Backlink(to = "order")
    private ToMany<OrderItem> orderItems;

    @Backlink(to = "order")
    private ToMany<OrderEntry> orderEntries;

    public Order() {
        this.customer = new ToOne<>(this, Order_.customer);
        this.user = new ToOne<>(this, Order_.user);
        this.table = new ToOne<>(this, Order_.table);
        this.delivery = new ToOne<>(this, Order_.delivery);
        //
        this.orderItems = new ToMany<>(this, Order_.orderItems);
        this.orderEntries = new ToMany<>(this, Order_.orderEntries);
    }
    
    public double getTaxRate() {
        List<OrderItem> items = this.orderItems.getListFactory().createList();
        return items.isEmpty() ? 0 : items.get(0).snapshot_taxRate;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getInPreparationDate() {
        return inPreparationDate;
    }

    public void setInPreparationDate(Date inPreparationDate) {
        this.inPreparationDate = inPreparationDate;
    }

    public Date getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(Date readyDate) {
        this.readyDate = readyDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public long getOrderType() {
        return orderType;
    }

    public void setOrderType(long orderType) {
        this.orderType = orderType;
    }

    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(long preparationTime) {
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

}
