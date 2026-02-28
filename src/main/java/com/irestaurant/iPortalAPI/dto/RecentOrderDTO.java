package com.irestaurant.iPortalAPI.dto;

import java.util.Date;

/**
 * Lightweight DTO representing a single recent order summary.
 * Used by OrderService#getRecentOrders to avoid exposing raw ObjectBox
 * entities.
 */
public class RecentOrderDTO {

    private long id;
    private String orderNumber;
    private String branchId;
    private String customerName;
    private double amount; // Computed: sum of (snapshot_price * snapshot_quantity) across all OrderItems
    private long orderStatus; // Raw ordinal from OrderStatuses enum
    private Date createdDate;

    public RecentOrderDTO() {
    }

    public RecentOrderDTO(long id,
            String orderNumber,
            String branchId,
            String customerName,
            double amount,
            long orderStatus,
            Date createdDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.branchId = branchId;
        this.customerName = customerName;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.createdDate = createdDate;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
