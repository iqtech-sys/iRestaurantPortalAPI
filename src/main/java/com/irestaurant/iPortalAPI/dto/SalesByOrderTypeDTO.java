package com.irestaurant.iPortalAPI.dto;

public class SalesByOrderTypeDTO {

    private String orderType;           // e.g. "DineIn", "TakeAway", "Delivery"
    private long orderCount;            // number of orders of this type
    private double grossRevenue;        // sum of (price - discount) × qty across all items
    private double averageOrderValue;   // grossRevenue / orderCount  (AOV)
    private double percentOfTotalRevenue; // (grossRevenue / total gross revenue) × 100

    public SalesByOrderTypeDTO() {
    }

    public SalesByOrderTypeDTO(String orderType, long orderCount, double grossRevenue,
                               double averageOrderValue, double percentOfTotalRevenue) {
        this.orderType             = orderType;
        this.orderCount            = orderCount;
        this.grossRevenue          = grossRevenue;
        this.averageOrderValue     = averageOrderValue;
        this.percentOfTotalRevenue = percentOfTotalRevenue;
    }

    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }

    public long getOrderCount() { return orderCount; }
    public void setOrderCount(long orderCount) { this.orderCount = orderCount; }

    public double getGrossRevenue() { return grossRevenue; }
    public void setGrossRevenue(double grossRevenue) { this.grossRevenue = grossRevenue; }

    public double getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(double averageOrderValue) { this.averageOrderValue = averageOrderValue; }

    public double getPercentOfTotalRevenue() { return percentOfTotalRevenue; }
    public void setPercentOfTotalRevenue(double percentOfTotalRevenue) { this.percentOfTotalRevenue = percentOfTotalRevenue; }
}
