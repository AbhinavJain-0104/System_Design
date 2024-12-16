package com.example.portfoliomanagement.service;

import com.example.portfoliomanagement.exception.InsufficientQuantityException;
import com.example.portfoliomanagement.exception.StockNotFoundException;
import com.example.portfoliomanagement.exception.UserNotFoundException;
import com.example.portfoliomanagement.model.PortfolioEntry;

import java.util.List;

public interface PortfolioService {
    void addStockToPortfolio(String userId, String stockId, int quantity) throws UserNotFoundException, StockNotFoundException; // Add stock
    void sellStockFromPortfolio(String userId, String stockId, int quantity) throws InsufficientQuantityException, UserNotFoundException, StockNotFoundException; // Sell stock
    double calculatePortfolioReturns(String userId) throws UserNotFoundException; // Calculate overall portfolio returns
    List<PortfolioEntry> getPortfolioDetails(String userId) throws UserNotFoundException; // Fetch all portfolio entries
}
