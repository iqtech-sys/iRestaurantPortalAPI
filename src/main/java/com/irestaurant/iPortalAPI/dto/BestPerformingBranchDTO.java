package com.irestaurant.iPortalAPI.dto;

public class BestPerformingBranchDTO {

    private String branchId;
    private double revenue;
    private double profit;
    private String currency;
    private long customerCount;
    private double growthRate;

    public BestPerformingBranchDTO() {
    }

    public BestPerformingBranchDTO(String branchId, double revenue, double profit, String currency, long customerCount,
            double growthRate) {
        this.branchId = branchId;
        this.revenue = revenue;
        this.profit = profit;
        this.currency = currency;
        this.customerCount = customerCount;
        this.growthRate = growthRate;
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

    public long getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(long customerCount) {
        this.customerCount = customerCount;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }
}
