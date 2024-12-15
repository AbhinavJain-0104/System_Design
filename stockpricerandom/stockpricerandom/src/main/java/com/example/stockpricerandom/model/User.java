package com.example.stockpricerandom.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String userId; // Unique identifier for the user
    private String userName; // Name of the user
    private Set<String> subscribedStocks; // Set of Stock IDs the user is subscribed to

    // Constructor
    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.subscribedStocks = new HashSet<>(); // Initialize as an empty set
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getSubscribedStocks() {
        return subscribedStocks;
    }

    public void setSubscribedStocks(Set<String> subscribedStocks) {
        this.subscribedStocks = subscribedStocks;
    }
}
