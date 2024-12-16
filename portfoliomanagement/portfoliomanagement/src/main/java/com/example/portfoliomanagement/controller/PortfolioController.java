package com.example.portfoliomanagement.controller;

import com.example.portfoliomanagement.exception.InsufficientQuantityException;
import com.example.portfoliomanagement.exception.StockNotFoundException;
import com.example.portfoliomanagement.exception.UserNotFoundException;
import com.example.portfoliomanagement.model.PortfolioEntry;
import com.example.portfoliomanagement.service.PortfolioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {

    private static final Logger logger = LoggerFactory.getLogger(PortfolioController.class);

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // Add stock to a user's portfolio
    @PostMapping("/{userId}/addStock")
    public ResponseEntity<String> addStockToPortfolio(@PathVariable String userId,
                                                      @RequestParam String stockId,
                                                      @RequestParam int quantity) throws UserNotFoundException, StockNotFoundException {
        logger.info("Received request to add stock '{}' to user '{}' portfolio.", stockId, userId);
        portfolioService.addStockToPortfolio(userId, stockId, quantity); // No purchasePrice
        return ResponseEntity.ok("Stock added to portfolio successfully.");
    }

    // Sell stock from a user's portfolio
    @PostMapping("/{userId}/sellStock")
    public ResponseEntity<String> sellStockFromPortfolio(@PathVariable String userId,
                                                         @RequestParam String stockId,
                                                         @RequestParam int quantity) throws UserNotFoundException, StockNotFoundException, InsufficientQuantityException {
        logger.info("Received request to sell stock '{}' from user '{}' portfolio.", stockId, userId);
        portfolioService.sellStockFromPortfolio(userId, stockId, quantity);
        return ResponseEntity.ok("Stock sold from portfolio successfully.");
    }

    // Get detailed portfolio of a user
    @GetMapping("/{userId}/details")
    public ResponseEntity<List<PortfolioEntry>> getPortfolioDetails(@PathVariable String userId) throws UserNotFoundException {
        logger.info("Received request to fetch portfolio details for user '{}'", userId);
        List<PortfolioEntry> portfolioEntries = portfolioService.getPortfolioDetails(userId);
        return ResponseEntity.ok(portfolioEntries);
    }

    // Calculate overall portfolio returns
    @GetMapping("/{userId}/returns")
    public ResponseEntity<Double> calculatePortfolioReturns(@PathVariable String userId) throws UserNotFoundException {
        logger.info("Received request to calculate portfolio returns for user '{}'", userId);
        double returns = portfolioService.calculatePortfolioReturns(userId);
        return ResponseEntity.ok(returns);
    }
}
