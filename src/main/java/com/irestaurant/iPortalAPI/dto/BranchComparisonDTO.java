package com.irestaurant.iPortalAPI.dto;

public class BranchComparisonDTO {

    private String branchId;
    private double revenue;
    private double profit;
    private String currency;
    private long ordersNumber;

    public BranchComparisonDTO() {
    }

    public BranchComparisonDTO(String branchId, double revenue, double profit, String currency, long ordersNumber) {
        this.branchId = branchId;
        this.revenue = revenue;
        this.profit = profit;
        this.currency = currency;
        this.ordersNumber = ordersNumber;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getOrdersNumber() {
        return ordersNumber;
    }

    public void setOrdersNumber(long ordersNumber) {
        this.ordersNumber = ordersNumber;
    }
}
