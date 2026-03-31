package com.irestaurant.iPortalAPI.dto;

public class RefundOrdersDTO {
    // Previous TopItem attributes
    private String categoryName;
    private double price;
    private long qtySold;
    private double revenueSubTotal;
    private double totalAmount;

    // Refund specific attributes
    private String branchName;
    private long totalOrders;
    private double lostRevenue;
    private double refundRatePercent;
    private String stopNote;

    public RefundOrdersDTO() {
    }

    public RefundOrdersDTO(String categoryName, double price, long qtySold, double revenueSubTotal, double totalAmount,
                           String branchName, long totalOrders, double lostRevenue, double refundRatePercent, String stopNote) {
        this.categoryName = categoryName;
        this.price = price;
        this.qtySold = qtySold;
        this.revenueSubTotal = revenueSubTotal;
        this.totalAmount = totalAmount;
        this.branchName = branchName;
        this.totalOrders = totalOrders;
        this.lostRevenue = lostRevenue;
        this.refundRatePercent = refundRatePercent;
        this.stopNote = stopNote;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public double getRevenueSubTotal() {
        return revenueSubTotal;
    }

    public void setRevenueSubTotal(double revenueSubTotal) {
        this.revenueSubTotal = revenueSubTotal;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getLostRevenue() {
        return lostRevenue;
    }

    public void setLostRevenue(double lostRevenue) {
        this.lostRevenue = lostRevenue;
    }

    public double getRefundRatePercent() {
        return refundRatePercent;
    }

    public void setRefundRatePercent(double refundRatePercent) {
        this.refundRatePercent = refundRatePercent;
    }

    public String getStopNote() {
        return stopNote;
    }

    public void setStopNote(String stopNote) {
        this.stopNote = stopNote;
    }
}
