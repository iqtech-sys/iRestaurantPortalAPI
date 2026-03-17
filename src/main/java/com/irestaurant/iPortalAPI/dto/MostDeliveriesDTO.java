package com.irestaurant.iPortalAPI.dto;

public class MostDeliveriesDTO {
    private String deliveryName;
    private long numberOfOrders;

    public MostDeliveriesDTO() {
    }

    public MostDeliveriesDTO(String deliveryName, long numberOfOrders) {
        this.deliveryName = deliveryName;
        this.numberOfOrders = numberOfOrders;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public long getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(long numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
