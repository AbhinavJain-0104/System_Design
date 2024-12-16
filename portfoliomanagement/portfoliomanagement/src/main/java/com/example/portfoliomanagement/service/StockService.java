package com.example.portfoliomanagement.service;

import com.example.portfoliomanagement.exception.StockAlreadyExistsException;
import com.example.portfoliomanagement.exception.StockNotFoundException;
import com.example.portfoliomanagement.model.Stock;

import java.util.List;

public interface StockService {
    void createStock(Stock stock) throws StockAlreadyExistsException; // Add a new stock
    void updateStockPrice(String stockId, double newPrice) throws StockNotFoundException; // Update stock price
    Stock getStockById(String stockId) throws StockNotFoundException; // Fetch stock by ID
    List<Stock> getAllStocks(); // Fetch all stocks
}
