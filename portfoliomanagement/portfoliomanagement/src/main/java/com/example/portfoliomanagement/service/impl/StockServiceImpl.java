package com.example.portfoliomanagement.service.impl;

import com.example.portfoliomanagement.exception.StockAlreadyExistsException;
import com.example.portfoliomanagement.exception.StockNotFoundException;
import com.example.portfoliomanagement.model.Portfolio;
import com.example.portfoliomanagement.model.PortfolioEntry;
import com.example.portfoliomanagement.model.Stock;
import com.example.portfoliomanagement.model.User;
import com.example.portfoliomanagement.service.StockService;
import com.example.portfoliomanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    private final UserService userService; // Inject UserService dependency

    public StockServiceImpl(UserService userService) {
        this.userService = userService; // Constructor-based injection of UserService
    }

    private final List<Stock> stocks = new ArrayList<>(); // In-memory stock storage

    @Override
    public void createStock(Stock stock) throws StockAlreadyExistsException {
        logger.info("Attempting to create a new stock with name: {}", stock.getStockName());

        // Check if the stock already exists (based on stock name)
        boolean stockExists = stocks.stream().anyMatch(existingStock -> existingStock.getStockName().equalsIgnoreCase(stock.getStockName()));
        if (stockExists) {
            logger.error("Stock with name '{}' already exists!", stock.getStockName());
            throw new StockAlreadyExistsException("Stock with name '" + stock.getStockName() + "' already exists.");
        }

        stocks.add(stock);
        logger.info("Stock '{}' created successfully.", stock.getStockName());
    }

    @Override
    public void updateStockPrice(String stockId, double newPrice) throws StockNotFoundException {
        logger.info("Attempting to update the price for stock ID: {}", stockId);

        // Fetch the stock and update its price
        Stock stock = getStockById(stockId);
        stock.setCurrentPrice(newPrice);
        logger.info("Stock '{}' price updated successfully. New Price: {}", stock.getStockName(), newPrice);

        // Update all portfolios that contain this stock
        logger.info("Updating portfolios associated with stock '{}'", stockId);

        for (User user : userService.getAllUsers()) { // Fetch all users
            Portfolio portfolio = user.getPortfolio();

            // Find the portfolio entry for this stock (if it exists)
            Optional<PortfolioEntry> entryOptional = portfolio.getPortfolioEntries()
                    .stream()
                    .filter(entry -> entry.getStockId().equals(stockId))
                    .findFirst();

            if (entryOptional.isPresent()) {
                PortfolioEntry entry = entryOptional.get();

                // Update current price and recalculate profit/loss for this stock
                entry.setCurrentPrice(newPrice);

                // Update portfolio-level metrics (totalProfit, totalLoss)
                double totalProfitOrLoss = portfolio.getPortfolioEntries()
                        .stream()
                        .mapToDouble(PortfolioEntry::getProfitOrLoss)
                        .sum();
                portfolio.setTotalProfit(Math.max(0, totalProfitOrLoss));
                portfolio.setTotalLoss(Math.min(0, totalProfitOrLoss));

                logger.info("Portfolio updated for user '{}' with new stock price. Stock: {}, Profit/Loss: {}", user.getUserName(), stock.getStockName(), entry.getProfitOrLoss());
            }
        }
    }



    @Override
    public Stock getStockById(String stockId) throws StockNotFoundException {
        logger.info("Fetching stock with ID: {}", stockId);

        // Search for the stock in the in-memory list
        return stocks.stream()
                .filter(stock -> stock.getStockId().equals(stockId))
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Stock with ID '{}' not found.", stockId);
                    return new StockNotFoundException("Stock not found with ID: " + stockId);
                });
    }

    @Override
    public List<Stock> getAllStocks() {
        logger.info("Fetching all stocks. Total stocks available: {}", stocks.size());
        return stocks;
    }

}
