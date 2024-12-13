package com.example.portfolio.model;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {
    private User user;
    private Map<String, PortfolioEntry> stocks; // Maps stock ID to PortfolioEntry
    private Double totalSpent; // Total amount spent on stocks
    private Double totalProfit; // Total profit from stocks
    private Double totalLoss; // Total loss from stocks

    public Portfolio(User user) {
        this.user = user;
        this.stocks = new HashMap<>();
        this.totalSpent = 0.0;
        this.totalProfit = 0.0;
        this.totalLoss = 0.0;
    }

    public User getUser() {
        return user;
    }

    public Map<String, PortfolioEntry> getStocks() {
        return stocks;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public Double getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(Double totalLoss) {
        this.totalLoss = totalLoss;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStocks(Map<String, PortfolioEntry> stocks) {
        this.stocks = stocks;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
}