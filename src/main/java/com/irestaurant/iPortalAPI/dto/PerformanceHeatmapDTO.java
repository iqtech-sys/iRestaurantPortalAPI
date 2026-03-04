package com.irestaurant.iPortalAPI.dto;

public class PerformanceHeatmapDTO {

    private String branchId;
    private double profit;
    private String month;

    public PerformanceHeatmapDTO() {
    }

    public PerformanceHeatmapDTO(String branchId, double profit, String month) {
        this.branchId = branchId;
        this.profit = profit;
        this.month = month;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    
}
