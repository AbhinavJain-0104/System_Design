package com.example.stockpricerandom.model;

import java.util.ArrayDeque;
import java.util.Deque;

    public class Stock {
        private String stockId; // Unique identifier for the stock
        private String stockSymbol; // Human-readable stock symbol (e.g., AAPL, GOOGL)
        private double currentPrice; // Current price of the stock
        private double originalPrice; // Original price of the stock when added
        private double minPrice; // Minimum price recorded
        private double maxPrice; // Maximum price recorded
        private Deque<Double> priceHistory; // Latest 10 prices for the stock



        // Constructor
        public Stock(String stockId, String stockSymbol, double originalPrice) {
            this.stockId = stockId;
            this.stockSymbol = stockSymbol;
            this.originalPrice = originalPrice;
            this.currentPrice = originalPrice;
            this.minPrice = originalPrice;
            this.maxPrice = originalPrice;
            this.priceHistory = new ArrayDeque<>(); // Initialize as an empty deque
            this.priceHistory.add(originalPrice); // Add the original price as the first entry
        }

        public String getStockId() {
            return stockId;
        }

        public void setStockId(String stockId) {
            this.stockId = stockId;
        }

        public String getStockSymbol() {
            return stockSymbol;
        }

        public void setStockSymbol(String stockSymbol) {
            this.stockSymbol = stockSymbol;
        }

        public double getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public double getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(double maxPrice) {
            this.maxPrice = maxPrice;
        }

        public Deque<Double> getPriceHistory() {
            return priceHistory;
        }

        public void setPriceHistory(Deque<Double> priceHistory) {
            this.priceHistory = priceHistory;
        }

}
