package com.example.stockpricerandom.controller;


import com.example.stockpricerandom.exceptions.DuplicateStockException;
import com.example.stockpricerandom.exceptions.StockNotFoundException;
import com.example.stockpricerandom.observer.ConsoleNotificationService;
import com.example.stockpricerandom.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;
    @Autowired
    private ConsoleNotificationService consoleNotificationService;

    @PostMapping
    public ResponseEntity<String> addStock(@RequestParam String stockId, @RequestParam String stockSymbol, @RequestParam double originalPrice) throws DuplicateStockException {
        stockService.addStock(stockId, stockSymbol, originalPrice);
        return ResponseEntity.ok("Stock added successfully.");
    }

    @DeleteMapping("/{stockId}")
    public ResponseEntity<String> deleteStock(@PathVariable String stockId) throws StockNotFoundException {
        stockService.deleteStock(stockId);
        return ResponseEntity.ok("Stock deleted successfully.");
    }

    @PutMapping("/{stockId}/price")
    public ResponseEntity<String> updateStockPrice(@PathVariable String stockId, @RequestParam double newPrice) throws StockNotFoundException {
        stockService.updatePrice(stockId, newPrice,consoleNotificationService);
        return ResponseEntity.ok("Stock price updated successfully.");
    }

    @GetMapping("/{stockId}/history")
    public ResponseEntity<List<Double>> getPriceHistory(@PathVariable String stockId) throws StockNotFoundException {
        return ResponseEntity.ok(stockService.getPriceHistory(stockId));
    }

    @GetMapping("/{stockId}/statistics")
    public ResponseEntity<Map<String, Double>> getStatistics(@PathVariable String stockId) throws StockNotFoundException {
        return ResponseEntity.ok(stockService.getStatistics(stockId));
    }
}