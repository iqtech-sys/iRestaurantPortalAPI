package com.irestaurant.iPortalAPI.dto;

public class SalesByCategoryDTO {

    private String category;
    private long unitsSold;
    private double revenue;
    private double percentOfTotalSales;
    private double grossMarginPercent; // [(Revenue - COGS) / Revenue] * 100

    public SalesByCategoryDTO() {
    }

    public SalesByCategoryDTO(String category, long unitsSold, double revenue, double percentOfTotalSales, double grossMarginPercent) {
        this.category = category;
        this.unitsSold = unitsSold;
        this.revenue = revenue;
        this.percentOfTotalSales = percentOfTotalSales;
        this.grossMarginPercent = grossMarginPercent;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public long getUnitsSold() { return unitsSold; }
    public void setUnitsSold(long unitsSold) { this.unitsSold = unitsSold; }

    public double getRevenue() { return revenue; }
    public void setRevenue(double revenue) { this.revenue = revenue; }

    public double getPercentOfTotalSales() { return percentOfTotalSales; }
    public void setPercentOfTotalSales(double percentOfTotalSales) { this.percentOfTotalSales = percentOfTotalSales; }

    public double getGrossMarginPercent() { return grossMarginPercent; }
    public void setGrossMarginPercent(double grossMarginPercent) { this.grossMarginPercent = grossMarginPercent; }
}
