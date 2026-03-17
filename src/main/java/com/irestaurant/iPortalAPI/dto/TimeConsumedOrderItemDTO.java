package com.irestaurant.iPortalAPI.dto;

/**
 * Lightweight item detail used inside TimeConsumedOrderDTO.
 */
public class TimeConsumedOrderItemDTO {

    private long id;
    private String title;
    private double price;
    private long quantity;
    private double discount;
    private String currency;

    public TimeConsumedOrderItemDTO() {
    }

    public TimeConsumedOrderItemDTO(long id, String title, double price, long quantity, double discount, String currency) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.currency = currency;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
