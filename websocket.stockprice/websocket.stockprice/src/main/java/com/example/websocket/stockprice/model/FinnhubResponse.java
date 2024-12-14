package com.example.websocket.stockprice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinnhubResponse {
    @JsonProperty("c") // Current price
    private double currentPrice;

    // Getter
    public double getCurrentPrice() {
        return currentPrice;
    }

    // Setter
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}