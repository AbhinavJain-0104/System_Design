package com.example.stock.price.model;

public class Stock {
    private String stockId;
    private double price;

    public Stock(String stockId, double price) {
        this.stockId = stockId;
        this.price = price;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
