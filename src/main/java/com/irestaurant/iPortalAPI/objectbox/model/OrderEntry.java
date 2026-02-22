package com.irestaurant.iPortalAPI.objectbox.model;

import com.irestaurant.iPortalAPI.converter.LocalDateTimeConverter;
import com.irestaurant.iPortalAPI.enumerators.OrderTypes;
import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class OrderEntry {

    @Id(assignable = true)
    long id;

    int orderType = OrderTypes.DineIn.ordinal();

    @Convert(converter = LocalDateTimeConverter.class, dbType = Long.class)
    LocalDateTime dateTime;

    String orderNum;

    String branchId;

    ToOne<Order> order;

    ToOne<Delivery> delivery;

    ToOne<Waiter> waiter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
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
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 79 * hash + this.orderType;
        hash = 79 * hash + Objects.hashCode(this.dateTime);
        hash = 79 * hash + Objects.hashCode(this.orderNum);
        hash = 79 * hash + Objects.hashCode(this.branchId);
        hash = 79 * hash + Objects.hashCode(this.order);
        hash = 79 * hash + Objects.hashCode(this.delivery);
        hash = 79 * hash + Objects.hashCode(this.waiter);
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
        final OrderEntry other = (OrderEntry) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.orderType != other.orderType) {
            return false;
        }
        if (!Objects.equals(this.orderNum, other.orderNum)) {
            return false;
        }
        if (!Objects.equals(this.branchId, other.branchId)) {
            return false;
        }
        if (!Objects.equals(this.dateTime, other.dateTime)) {
            return false;
        }
        if (!Objects.equals(this.order, other.order)) {
            return false;
        }
        if (!Objects.equals(this.delivery, other.delivery)) {
            return false;
        }
        return Objects.equals(this.waiter, other.waiter);
    }

    @Override
    public String toString() {
        return "OrderEntry{" + "id=" + id + ", orderType=" + orderType + ", dateTime=" + dateTime + ", orderNum="
                + orderNum + ", branchId=" + branchId + ", order=" + order + ", delivery=" + delivery + ", waiter="
                + waiter + '}';
    }
}
