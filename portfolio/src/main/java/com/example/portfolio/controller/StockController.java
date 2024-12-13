package com.example.portfolio.controller;

import com.example.portfolio.model.Stocks;
import com.example.portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService; // Initialize the stock service
    }

    // Method to add a new stock
    @PostMapping
    public ResponseEntity<String> addStock(@RequestBody Stocks stock) {
        stockService.addStock(stock);
        return new ResponseEntity<>("Stock added successfully.", HttpStatus.CREATED);
    }

    // Method to remove a stock
    @DeleteMapping("/{stockId}")
    public ResponseEntity<String> removeStock(@PathVariable String stockId) {
        stockService.removeStock(stockId);
        return new ResponseEntity<>("Stock removed successfully.", HttpStatus.OK);
    }

    // Method to get a stock by ID
    @GetMapping("/{stockId}")
    public ResponseEntity<Stocks> getStock(@PathVariable String stockId) {
        Stocks stock = stockService.getStockById(stockId);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    // Method to get all stocks
    @GetMapping
    public ResponseEntity<Map<String, Stocks>> getAllStocks() {
        Map<String, Stocks> stocks = stockService.getAllStocks();
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    // Method to update stock price and quantity
    @PutMapping("/{stockId}")
    public ResponseEntity<String> updateStock(@PathVariable String stockId, @RequestParam Double newPrice, @RequestParam Integer newQuantity) {
        stockService.updateStockPrice(stockId, newPrice);
        stockService.updateStockQuantity(stockId, newQuantity);
        return new ResponseEntity<>("Stock updated successfully.", HttpStatus.OK);
    }
}