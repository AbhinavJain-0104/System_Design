package com.example.websocket.stockprice.model;

public class Stock {
    private final String symbol; // Stock symbol (e.g., "AAPL")
    private double price; // Current price of the stock

    // Constructor
    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    /**Setter for price
     * *  (no setter for symbol to maintain immutability) -> meaning once we receive a symbol from api
     * we wont be changing it hence we dont need setter
      */

    public void setPrice(double price) {
        this.price = price;
    }
}