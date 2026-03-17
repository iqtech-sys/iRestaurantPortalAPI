package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.enumerators.OrderTypes;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Sync;
import io.objectbox.relation.ToOne;
import java.util.Date;

@Sync
@Entity
public class OrderEntry {

    @Id(assignable = true)
    long id;

    long orderType = OrderTypes.DineIn.ordinal();

    //@Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    Date dateTime;

    String orderNum;

    String branchId;

    public ToOne<Order> order;
    public ToOne<Delivery> delivery;
    public ToOne<Waiter> waiter;

    public OrderEntry() {
        this.order = new ToOne<>(this, OrderEntry_.order);
        this.delivery = new ToOne<>(this, OrderEntry_.delivery);
        this.waiter = new ToOne<>(this, OrderEntry_.waiter);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderType() {
        return orderType;
    }

    public void setOrderType(long orderType) {
        this.orderType = orderType;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public ToOne<Order> getOrder() {
        return order;
    }

    public void setOrder(ToOne<Order> order) {
        this.order = order;
    }

    public ToOne<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(ToOne<Delivery> delivery) {
        this.delivery = delivery;
    }

    public ToOne<Waiter> getWaiter() {
        return waiter;
    }

    public void setWaiter(ToOne<Waiter> waiter) {
        this.waiter = waiter;
    }
    
    
}
