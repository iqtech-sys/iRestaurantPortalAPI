package com.irestaurant.iPortalAPI.dto;

public class SalesGadgetsDTO {
    private double expenses;
    private double profit;
    private double revenue;

    public SalesGadgetsDTO() {
    }

    public SalesGadgetsDTO(double expenses, double profit, double revenue) {
        this.expenses = expenses;
        this.profit = profit;
        this.revenue = revenue;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
