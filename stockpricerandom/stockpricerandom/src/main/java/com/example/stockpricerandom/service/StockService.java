package com.example.stockpricerandom.service;

import com.example.stockpricerandom.exceptions.DuplicateStockException;
import com.example.stockpricerandom.exceptions.StockNotFoundException;
import com.example.stockpricerandom.model.Stock;
import com.example.stockpricerandom.observer.Observer;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StockService {
    // A map to store all stocks, with stockId as the key
    private Map<String, Stock> stocks = new HashMap<>();

    /**
     * @param stockId      The unique identifier for the stock.
     * @param stockSymbol  The human-readable symbol for the stock (e.g., "AAPL").
     * @param originalPrice The original price of the stock when added.
     */

    public void addStock(String stockId, String stockSymbol, double originalPrice) throws DuplicateStockException {
        if (stocks.containsKey(stockId)) {
            throw new DuplicateStockException("Stock with ID " + stockId + " already exists.");
        }
        stocks.put(stockId, new Stock(stockId, stockSymbol, originalPrice));
    }

    /**
     * Deletes a stock from the system.
     *
     * @param stockId The unique identifier of the stock to delete.
     */
    public void deleteStock(String stockId) throws StockNotFoundException {
        if (!stocks.containsKey(stockId)) {
            throw new StockNotFoundException("Stock with ID " + stockId + " not found.");
        }
        stocks.remove(stockId);
    }

    /**
     * Updates the price of a stock and manages price history, minPrice, maxPrice, and notifications.
     *
     * @param stockId  The unique identifier of the stock.
     * @param newPrice The new price to update.
     */
    public void updatePrice(String stockId, double newPrice, Observer notificationService) throws StockNotFoundException {
        if (!stocks.containsKey(stockId)) {
            throw new StockNotFoundException("Stock with ID " + stockId + " not found.");
        }

        Stock stock = stocks.get(stockId);
        double oldPrice = stock.getCurrentPrice();
        stock.setCurrentPrice(newPrice);

        // Update minPrice and maxPrice
        if (newPrice < stock.getMinPrice()) {
            stock.setMinPrice(newPrice);
        }
        if (newPrice > stock.getMaxPrice()) {
            stock.setMaxPrice(newPrice);
        }

        // Update price history (keep only the latest 10 prices)
        Deque<Double> priceHistory = stock.getPriceHistory();
        if (priceHistory.size() == 10) {
            priceHistory.pollFirst(); // Remove the oldest price
        }
        priceHistory.addLast(newPrice);

        // Check for significant price change (>5%) and notify observers
        if (Math.abs((newPrice - oldPrice) / oldPrice) > 0.05) {
            String message = "Stock " + stock.getStockSymbol() + " price changed by more than 5%. Old Price: "
                    + oldPrice + ", New Price: " + newPrice;
            // Use the dynamically provided notification service
            notificationService.notify(message);
        }

        String message = "Stock " + stock.getStockSymbol() + " price changed . Old Price: "
                + oldPrice + ", New Price: " + newPrice;
        // Use the dynamically provided notification service
        notificationService.notify(message);
    }


    /**
     * Fetches the latest 10 price history for a stock.
     *
     * @param stockId The unique identifier of the stock.
     * @return A list of the latest 10 prices.
     */
    public List<Double> getPriceHistory(String stockId) throws StockNotFoundException {
        if (!stocks.containsKey(stockId)) {
            throw new StockNotFoundException("Stock with ID " + stockId + " not found.");
        }
        return new ArrayList<>(stocks.get(stockId).getPriceHistory()); // Return a copy to avoid modification
    }


    /**
     * Calculates statistics for a stock: min price, max price, and average of the last 10 prices.
     *
     * @param stockId The unique identifier of the stock.
     * @return A map containing "min", "max", and "average" statistics.
     */
    public Map<String, Double> getStatistics(String stockId) throws StockNotFoundException {
        if (!stocks.containsKey(stockId)) {
            throw new StockNotFoundException("Stock with ID " + stockId + " not found.");
        }

        Stock stock = stocks.get(stockId);
        Deque<Double> priceHistory = stock.getPriceHistory();

        // Calculate average price from the latest 10 prices
        double average = priceHistory.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("min", stock.getMinPrice());
        statistics.put("max", stock.getMaxPrice());
        statistics.put("average", average);
        return statistics;
    }

    /**
     * Fetches all tracked stocks.
     *
     * @return A list of all stocks in the system.
     */
    public List<Stock> getAllTrackedStocks() {
        return new ArrayList<>(stocks.values());
    }



}
