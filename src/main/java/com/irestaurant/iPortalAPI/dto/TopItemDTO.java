package com.irestaurant.iPortalAPI.dto;

public class TopItemDTO {

    private String name;
    private String category;
    private double price;
    private long qtySold;
    private double revenue;
    private double totalAmount;

    public TopItemDTO() {
    }

    public TopItemDTO(String name, String category, double price, long qtySold, double revenue, double totalAmount) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.qtySold = qtySold;
        this.revenue = revenue;
        this.totalAmount = totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQtySold() {
        return qtySold;
    }

    public void setQtySold(long qtySold) {
        this.qtySold = qtySold;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
