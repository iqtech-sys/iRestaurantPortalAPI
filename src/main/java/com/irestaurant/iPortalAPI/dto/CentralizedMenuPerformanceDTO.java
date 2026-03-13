package com.irestaurant.iPortalAPI.dto;

public class CentralizedMenuPerformanceDTO {

    private String menuItem;
    private double totalSales;
    private String bestSellBranch;
    private String worstSellBranch;

    public CentralizedMenuPerformanceDTO() {
    }

    public CentralizedMenuPerformanceDTO(String menuItem, double totalSales, String bestSellBranch, String worstSellBranch) {
        this.menuItem = menuItem;
        this.totalSales = totalSales;
        this.bestSellBranch = bestSellBranch;
        this.worstSellBranch = worstSellBranch;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public String getBestSellBranch() {
        return bestSellBranch;
    }

    public void setBestSellBranch(String bestSellBranch) {
        this.bestSellBranch = bestSellBranch;
    }

    public String getWorstSellBranch() {
        return worstSellBranch;
    }

    public void setWorstSellBranch(String worstSellBranch) {
        this.worstSellBranch = worstSellBranch;
    }
}
