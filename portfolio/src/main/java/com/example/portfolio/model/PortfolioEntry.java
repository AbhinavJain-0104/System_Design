package com.example.portfolio.model;

/**
 * Represents an entry in a user's portfolio for a specific stock.
 */
public class PortfolioEntry {
    private String stockId;
    private double originalPurchasePrice; // Original purchase price
    private double currentPrice; // Current market price
    private int quantity;
    private Double totalSpent; // Total amount spent on stocks
    private Double totalProfit; // Total profit from stocks
    private Double totalLoss; // Total loss from stocks

    public PortfolioEntry(String stockId, double originalPurchasePrice, int quantity) {
        this.stockId = stockId;
        this.originalPurchasePrice = originalPurchasePrice;
        this.currentPrice = originalPurchasePrice; // Initially, current price is the same as purchase price
        this.quantity = quantity;
        this.totalSpent=originalPurchasePrice*quantity;
        this.totalProfit=0.0;
        this.totalLoss=0.0;
    }

    public String getStockId() {
        return stockId;
    }

    public double getOriginalPurchasePrice() {
        return originalPurchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice; // Update current price
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setOriginalPurchasePrice(double originalPurchasePrice) {
        this.originalPurchasePrice = originalPurchasePrice;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Double getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(Double totalLoss) {
        this.totalLoss = totalLoss;
    }
}