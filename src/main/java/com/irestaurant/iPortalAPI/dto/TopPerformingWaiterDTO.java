package com.irestaurant.iPortalAPI.dto;

public class TopPerformingWaiterDTO {

    private String waiterName;
    private long numberOfOrders;

    public TopPerformingWaiterDTO() {
    }

    public TopPerformingWaiterDTO(String waiterName, long numberOfOrders) {
        this.waiterName = waiterName;
        this.numberOfOrders = numberOfOrders;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
