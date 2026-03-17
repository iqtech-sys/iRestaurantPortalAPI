package com.irestaurant.iPortalAPI.dto;

import java.util.List;

/**
 * DTO returned by getTopMostTimeConsumedOrders.
 * timeConsumedMs = deliveredDate.getTime() - createdDate.getTime()  (milliseconds)
 */
public class TimeConsumedOrderDTO {

    private long orderId;
    private String orderNumber;
    private long timeConsumedMs; // Duration from createdDate to deliveredDate in milliseconds
    private List<TimeConsumedOrderItemDTO> orderItems;

    public TimeConsumedOrderDTO() {
    }

    public TimeConsumedOrderDTO(long orderId, String orderNumber, long timeConsumedMs, List<TimeConsumedOrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.timeConsumedMs = timeConsumedMs;
        this.orderItems = orderItems;
    }

    public long getOrderId() { return orderId; }
    public void setOrderId(long orderId) { this.orderId = orderId; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public long getTimeConsumedMs() { return timeConsumedMs; }
    public void setTimeConsumedMs(long timeConsumedMs) { this.timeConsumedMs = timeConsumedMs; }

    public List<TimeConsumedOrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<TimeConsumedOrderItemDTO> orderItems) { this.orderItems = orderItems; }
}
