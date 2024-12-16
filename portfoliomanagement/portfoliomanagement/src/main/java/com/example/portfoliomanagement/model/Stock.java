package com.example.portfoliomanagement.model;

import java.util.UUID;

public class Stock {
    private String stockId; // Unique identifier for the stock
    private String stockName; // Name of the stock
    private double currentPrice; // Latest price of the stock

    // Constructor
    public Stock(String stockName, double currentPrice) {
        this.stockId = UUID.randomUUID().toString(); // Auto-generate a unique ID
        this.stockName = stockName;
        this.currentPrice = currentPrice;
    }

    // Getters and Setters
    public String getStockId() {
        return stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
