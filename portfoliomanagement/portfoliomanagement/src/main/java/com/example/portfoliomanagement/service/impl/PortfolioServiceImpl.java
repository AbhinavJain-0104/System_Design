package com.example.portfoliomanagement.service.impl;

import com.example.portfoliomanagement.exception.InsufficientQuantityException;
import com.example.portfoliomanagement.exception.StockNotFoundException;
import com.example.portfoliomanagement.exception.UserNotFoundException;
import com.example.portfoliomanagement.model.Portfolio;
import com.example.portfoliomanagement.model.PortfolioEntry;
import com.example.portfoliomanagement.model.Stock;
import com.example.portfoliomanagement.model.User;
import com.example.portfoliomanagement.service.PortfolioService;
import com.example.portfoliomanagement.service.StockService;
import com.example.portfoliomanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);

    private final UserService userService; // Dependency on UserService
    private final StockService stockService; // Dependency on StockService

    public PortfolioServiceImpl(UserService userService, StockService stockService) {
        this.userService = userService;
        this.stockService = stockService;
    }

    @Override
    public void addStockToPortfolio(String userId, String stockId, int quantity) throws StockNotFoundException, UserNotFoundException {
        logger.info("Adding stock '{}' to user '{}' portfolio with quantity: {}", stockId, userId, quantity);

        // Fetch the user and their portfolio
        User user = userService.getUserById(userId);
        Portfolio portfolio = user.getPortfolio();

        // Fetch the stock to get its current price
        Stock stock = stockService.getStockById(stockId);
        double purchasePrice = stock.getCurrentPrice(); // Use current stock price as the purchase price

        PortfolioEntry existingEntry = portfolio.getPortfolioEntries()
                .stream()
                .filter(entry -> entry.getStockId().equals(stockId))
                .findFirst()
                .orElse(null);

        if (existingEntry == null) {
            // Add a new portfolio entry for this stock
            PortfolioEntry newEntry = new PortfolioEntry(
                    stockId, stock.getStockName(), quantity, purchasePrice, stock.getCurrentPrice());
            portfolio.getPortfolioEntries().add(newEntry);
            logger.info("New stock '{}' added to portfolio.", stockId);
        } else {
            // Update the existing portfolio entry
            int newQuantity = existingEntry.getQuantity() + quantity;
            double totalSpent = (existingEntry.getAveragePurchasePrice() * existingEntry.getQuantity()) +
                    (purchasePrice * quantity);
            existingEntry.setQuantity(newQuantity);
            existingEntry.setAveragePurchasePrice(totalSpent / newQuantity);
            logger.info("Stock '{}' in portfolio updated. New quantity: {}, New average price: {}",
                    stockId, newQuantity, existingEntry.getAveragePurchasePrice());
        }

        portfolio.setNetSpent(portfolio.getNetSpent() + (purchasePrice * quantity));
        logger.info("Net spent for portfolio updated. Total Net Spent: {}", portfolio.getNetSpent());
    }

    @Override
    public void sellStockFromPortfolio(String userId, String stockId, int quantity) throws InsufficientQuantityException, UserNotFoundException, StockNotFoundException {
        logger.info("Attempting to sell stock '{}' from user '{}' portfolio with quantity: {}", stockId, userId, quantity);

        User user = userService.getUserById(userId);
        Portfolio portfolio = user.getPortfolio();

        PortfolioEntry entry = portfolio.getPortfolioEntries()
                .stream()
                .filter(e -> e.getStockId().equals(stockId))
                .findFirst()
                .orElseThrow(() -> new StockNotFoundException("Stock not found in portfolio: " + stockId));

        if (entry.getQuantity() < quantity) {
            logger.error("Insufficient quantity for stock '{}' in portfolio. Available: {}", stockId, entry.getQuantity());
            throw new InsufficientQuantityException("Not enough stock to sell. Available: " + entry.getQuantity());
        }

        entry.setQuantity(entry.getQuantity() - quantity);
        if (entry.getQuantity() == 0) {
            portfolio.getPortfolioEntries().remove(entry);
            logger.info("Stock '{}' removed from portfolio as quantity is now 0.", stockId);
        }
    }

    @Override
    public double calculatePortfolioReturns(String userId) throws UserNotFoundException {
        logger.info("Calculating portfolio returns for user '{}'", userId);

        User user = userService.getUserById(userId);
        Portfolio portfolio = user.getPortfolio();

        double returns = portfolio.getPortfolioEntries()
                .stream()
                .mapToDouble(entry -> (entry.getCurrentPrice() - entry.getAveragePurchasePrice()) * entry.getQuantity())
                .sum();

        logger.info("Portfolio returns for user '{}' calculated as: {}", userId, returns);
        return returns;
    }

    @Override
    public List<PortfolioEntry> getPortfolioDetails(String userId) throws UserNotFoundException {
        logger.info("Fetching portfolio details for user '{}'", userId);

        User user = userService.getUserById(userId);
        return user.getPortfolio().getPortfolioEntries();
    }
}
