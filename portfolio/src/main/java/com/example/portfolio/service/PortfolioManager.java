package com.example.portfolio.service;

import com.example.portfolio.model.Portfolio;
import com.example.portfolio.model.PortfolioEntry;
import com.example.portfolio.model.Stocks;
import com.example.portfolio.model.User;
import org.springframework.stereotype.Service;


import static java.lang.Math.abs;

/**
 * PortfolioManager class to manage user portfolios and stock transactions.
 */
@Service
public class PortfolioManager {
    private UserService userService; // Service to manage users
    private StockService stockService; // Service to retrieve stock information

    public PortfolioManager(UserService userService, StockService stockService) {
        this.userService = userService; // Initialize the user service
        this.stockService = stockService; // Initialize the stock service
    }

    // Method to add a stock to the user's portfolio
    public void addStockToPortfolio(String userId, String stockId, int quantity) {
        User user = userService.getUserById(userId);
        if (user == null) {throw new IllegalArgumentException("User not found.");}

        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null) {throw new IllegalArgumentException("User does not have a portfolio.");}

        Stocks stocks= stockService.getStockById(stockId);
        if (stocks == null) {throw new IllegalArgumentException("stock not found.");}

        double purchasePrice=stocks.getCurrentPrice();


        // Create a new PortfolioEntry for the stock
        PortfolioEntry entry = portfolio.getStocks().get(stockId);
        if (entry != null) {
            // If the stock already exists, update the quantity
            entry.setQuantity(entry.getQuantity() + quantity);
            entry.setTotalSpent(entry.getTotalSpent()+(purchasePrice*quantity));
            entry.setCurrentPrice(purchasePrice);

        } else {
            // If it's a new stock, create a new entry
            entry = new PortfolioEntry(stockId, purchasePrice, quantity);
            portfolio.getStocks().put(stockId, entry);
        }

        calculatePortfolioMetrics(portfolio); // Recalculate metrics
    }

    // Method to update the stock price in the portfolio
    public void updateStockPrice(String stockId, double newPrice) {
        for (User user : userService.getAllUsers().values()) {
            Portfolio portfolio = user.getPortfolio();
            if (portfolio != null) {
                PortfolioEntry entry = portfolio.getStocks().get(stockId);
                if (entry != null) {
                    // Update the current price in the portfolio entry
                    entry.setCurrentPrice(newPrice);
                    calculatePortfolioMetrics(portfolio); // Recalculate metrics
                }
            }
        }
    }

    //Method to remove the stock from all users and recalculate
    public void deleteStockPrice(String stockId) {
        for (User user : userService.getAllUsers().values()) {
            Portfolio portfolio = user.getPortfolio();
            if (portfolio != null) {
                PortfolioEntry entry = portfolio.getStocks().get(stockId);
                if (entry != null) {
                    // Update the current price in the portfolio entry
                    portfolio.getStocks().remove(stockId);
                    calculatePortfolioMetrics(portfolio); // Recalculate metrics
                }
            }
        }
    }

    // Method to calculate total investment, profit, and loss for the portfolio
    public void calculatePortfolioMetrics(Portfolio portfolio) {
        double totalInvestment = 0;
        double totalProfit = 0;
        double totalLoss = 0;

        for (PortfolioEntry entry : portfolio.getStocks().values()) {
            totalInvestment += entry.getTotalSpent();
            double profitLoss = (entry.getCurrentPrice() * entry.getQuantity()) - entry.getTotalSpent();

            // Update total profit and loss based on the profitLoss value
            if (profitLoss > 0) {
                totalProfit += profitLoss;
                entry.setTotalProfit(profitLoss);
                entry.setTotalLoss(0.00);
            } else {
                totalLoss -= profitLoss; // Subtracting a negative to add to total loss
                entry.setTotalLoss(abs(profitLoss));
                entry.setTotalProfit(0.00);
            }
        }
        double totalLoss1=totalLoss;
        double totalProfit1=totalProfit;

        if(totalProfit==totalLoss){
            totalLoss1=0;
            totalProfit1=0;
        }


        totalLoss1 = (totalProfit >totalLoss) ? 0 : totalLoss-totalProfit;
        totalProfit1 = (totalProfit > totalLoss) ? totalProfit-totalLoss : 0;

        totalLoss=totalLoss1;
        totalProfit=totalProfit1;

        portfolio.setTotalSpent(totalInvestment);
        portfolio.setTotalProfit(totalProfit);
        portfolio.setTotalLoss(totalLoss);
    }


    // Method to sell stocks using only stock ID
    public void sellSomeStockFromPortfolio(String userID,String stockId, int quantity) {
        User user = userService.getUserById(userID);
        if (user == null) {throw new IllegalArgumentException("User not found.");}

        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null) {throw new IllegalArgumentException("User does not have a portfolio.");}

        Stocks stocks= stockService.getStockById(stockId);
        if (stocks == null) {throw new IllegalArgumentException("stock not found.");}

        PortfolioEntry entry = portfolio.getStocks().get(stockId);

        if (entry != null && entry.getQuantity() >= quantity) {
            entry.setQuantity(entry.getQuantity() - quantity);
            entry.setTotalSpent(entry.getTotalSpent()- entry.getOriginalPurchasePrice()*quantity);
            // If quantity becomes zero, remove the entry from the portfolio
            if (entry.getQuantity() == 0) {
                portfolio.getStocks().remove(stockId);
            }
            calculatePortfolioMetrics(portfolio); // Recalculate metrics
        } else {
            throw new IllegalArgumentException("Insufficient stock quantity to sell.");
        }
    }

}