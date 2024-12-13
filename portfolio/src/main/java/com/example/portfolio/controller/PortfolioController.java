package com.example.portfolio.controller;

import com.example.portfolio.model.Portfolio;
import com.example.portfolio.model.User;
import com.example.portfolio.service.PortfolioManager;
import com.example.portfolio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    private final PortfolioManager portfolioManager;
    private final UserService userService;

    @Autowired
    public PortfolioController(PortfolioManager portfolioManager, UserService userService) {
        this.portfolioManager = portfolioManager; // Initialize the portfolio manager
        this.userService = userService; // Initialize the user service
    }

    // Method to purchase stocks for a user
    @PostMapping("/{userId}/stocks")
    public ResponseEntity<String> purchaseStock(@PathVariable String userId, @RequestParam String stockId, @RequestParam int quantity) {
        portfolioManager.addStockToPortfolio(userId, stockId, quantity);
        return new ResponseEntity<>("Stock purchased successfully.", HttpStatus.OK);
    }

    // Method to sell stocks for a user
    @PostMapping("/sell/{userId}/stocks")
    public ResponseEntity<String> sellStock(@PathVariable String userId, @RequestParam String stockId, @RequestParam int quantity) {
        portfolioManager.sellSomeStockFromPortfolio(userId, stockId, quantity);
        return new ResponseEntity<>("Stock sold successfully.", HttpStatus.OK);
    }

    // Method to get the portfolio for a user
    @GetMapping("/{userId}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        if (user == null || user.getPortfolio() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User or portfolio not found
        }
        Portfolio portfolio = user.getPortfolio();
        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }

    // Method to calculate percentage return for a user
//    @GetMapping("/{userId}/percentage-return")
//    public ResponseEntity<Double> calculatePercentageReturn(@PathVariable String userId) {
//        double percentageReturn = portfolioManager.calculatePercentageReturn(userId);
//        return new ResponseEntity<>(percentageReturn, HttpStatus.OK);
//    }
}