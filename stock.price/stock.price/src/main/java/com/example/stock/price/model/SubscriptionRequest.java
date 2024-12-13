package com.example.stock.price.model;

public class SubscriptionRequest {
    private String stockId;
    private String clientId;

    // Getters and Setters
    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
