package com.example.websocket.stockprice.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username; // User's name
    private List<String> subscribedStocks; // List of stocks the user is subscribed to

    // Constructor
    public User(String username) {
        this.username = username;
        this.subscribedStocks = new ArrayList<>(); // Initialize the list
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getSubscribedStocks() {
        return subscribedStocks;
    }

    public void addSubscribedStock(String stockSymbol) {
        subscribedStocks.add(stockSymbol);
    }

    public void removeSubscribedStock(String stockSymbol) {
        subscribedStocks.remove(stockSymbol);
    }
}