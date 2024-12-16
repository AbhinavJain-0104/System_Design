package com.example.portfoliomanagement.model;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private String portfolioId; // Unique ID for the portfolio
    private String userId; // ID of the user who owns this portfolio
    private List<PortfolioEntry> portfolioEntries; // List of stocks owned
    private double netSpent; // Total money spent on all stocks
    private double totalProfit; // Total profit across all stocks
    private double totalLoss; // Total loss across all stocks

    // Constructor
    public Portfolio(String userId) {
        this.portfolioId = "PORTFOLIO-" + userId;
        this.userId = userId;
        this.portfolioEntries = new ArrayList<>(); // Initialize empty list
        this.netSpent = 0.0;
        this.totalProfit = 0.0;
        this.totalLoss = 0.0;
    }

    // Getters and Setters
    public String getPortfolioId() {
        return portfolioId;
    }

    public String getUserId() {
        return userId;
    }

    public List<PortfolioEntry> getPortfolioEntries() {
        return portfolioEntries;
    }

    public void setPortfolioEntries(List<PortfolioEntry> portfolioEntries) {
        this.portfolioEntries = portfolioEntries;
    }

    public double getNetSpent() {
        return netSpent;
    }

    public void setNetSpent(double netSpent) {
        this.netSpent = netSpent;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getTotalLoss() {
        return totalLoss;
    }

    public void setTotalLoss(double totalLoss) {
        this.totalLoss = totalLoss;
    }
}
