package com.example.portfoliomanagement.model;

import java.util.UUID;

public class User {
    private String userId; // Unique identifier for the user
    private String userName; // Name of the user
    private Portfolio portfolio; // Portfolio associated with the user

    // Constructor
    public User(String userName) {
        this.userId = UUID.randomUUID().toString(); // Auto-generate a unique ID
        this.userName = userName;
        this.portfolio = new Portfolio(this.userId); // Initialize the portfolio
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
