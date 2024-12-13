package com.example.portfolio.service;

import com.example.portfolio.model.Stocks;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockService {
    private static StockService stockService; // Singleton instance
    private Map<String, Stocks> stocks; // Maps stock ID to Stocks object
    private PortfolioManager portfolioManager; // Reference to PortfolioManager

//lazy says ki poora object nahi bas portfolio ka reference le ke aao-> to avoid circular dependency
    private StockService(@Lazy PortfolioManager portfolioManager) {
        this.stocks = new HashMap<>();
        this.portfolioManager = portfolioManager; // Initialize the portfolio manager
    }

    // Method to get the Singleton instance
  public static StockService getStockService(PortfolioManager portfolioManager){
        if(stockService==null){
            stockService = new StockService(portfolioManager);
        }
        return stockService;
  }

    // Method to add a new stock
    public void addStock(Stocks stock) {
        if (stocks.containsKey(stock.getId())) {
            throw new IllegalArgumentException("Stock with this ID already exists.");
        }
        stocks.put(stock.getId(), stock);
    }

    // Method to remove a stock
    public void removeStock(String stockId) {
        if (!stocks.containsKey(stockId)) {
            throw new IllegalArgumentException("Stock not found.");
        }
        // Notify PortfolioManager to delete this stock from all user portfolios
        portfolioManager.deleteStockPrice(stockId);
        stocks.remove(stockId);
    }

    // Method to get a stock by ID
    public Stocks getStockById(String stockId) {
        return stocks.get(stockId);
    }

    // Method to get all stocks
    public Map<String, Stocks> getAllStocks() {
        return stocks;
    }

    // Method to update stock price
    public void updateStockPrice(String stockId, Double newPrice) {
        Stocks stock = getStockById(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found.");
        }
        stock.setCurrentPrice(newPrice);
     // Notify PortfolioManager to update all portfolios with this stock
     portfolioManager.updateStockPrice(stockId, newPrice);
    }

    // Method to update stock quantity
    public void updateStockQuantity(String stockId, Integer newQuantity) {
        Stocks stock = getStockById(stockId);
        if (stock == null) {
            throw new IllegalArgumentException("Stock not found.");
        }
        stock.setTotalQuantity(newQuantity);
    }


}