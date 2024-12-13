package com.example.stock.price.service;

import com.example.stock.price.exceptions.StockNotFoundException;
import com.example.stock.price.model.StockPriceProvider;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class StockPriceSimulator implements StockPriceProvider {

    private final Map<String, Double> stockPrices = new ConcurrentHashMap<>();
    private final Random random = new Random();
    private final SubscriptionManager subscriptionManager; // Add SubscriptionManager

    public StockPriceSimulator(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    // Method to fetch the stock price
    @Override
    public double getStockPrice(String stockId) throws StockNotFoundException {
        Double price = stockPrices.get(stockId);
        if (price == null) {
            throw new StockNotFoundException("Stock not found: " + stockId);
        }
        return price;
    }

    // Method to add a new stock
    public void addStock(String stockId, double initialPrice) {
        stockPrices.put(stockId, initialPrice);
    }

    // Method to check if a stock exists
    public boolean stockExists(String stockId) {
        return stockPrices.containsKey(stockId);
    }


    // Method to start simulating stock prices
    public void startSimulatingStockPrices() {
        new Thread(() -> {
            while (true) {
                for (String stockId : stockPrices.keySet()) {
                    double newPrice = stockPrices.get(stockId) + random.nextDouble() - 0.5; // Small random change
                    stockPrices.put(stockId, newPrice);
                    subscriptionManager.notifyClients(stockId, newPrice); // Notify clients of the new price
                }
                try {
                    Thread.sleep(1000); // Simulate update every 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Stock price simulation interrupted: " + e.getMessage());
                }
            }
        }).start();
    }
}