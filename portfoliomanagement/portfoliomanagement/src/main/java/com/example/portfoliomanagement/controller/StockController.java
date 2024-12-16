package com.example.portfoliomanagement.controller;

import com.example.portfoliomanagement.exception.StockAlreadyExistsException;
import com.example.portfoliomanagement.exception.StockNotFoundException;
import com.example.portfoliomanagement.model.Stock;
import com.example.portfoliomanagement.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Create a new stock
    @PostMapping("/create")
    public ResponseEntity<String> createStock(@RequestBody Stock stock) throws StockAlreadyExistsException {
        logger.info("Received request to create stock: {}", stock.getStockName());
        stockService.createStock(stock);
        return ResponseEntity.ok("Stock created successfully with ID: " + stock.getStockId());
    }

    // Update the price of a stock
    @PutMapping("/update/{stockId}")
    public ResponseEntity<String> updateStockPrice(@PathVariable String stockId, @RequestParam double newPrice) throws StockNotFoundException {
        logger.info("Received request to update price for stock ID '{}'. New Price: {}", stockId, newPrice);
        stockService.updateStockPrice(stockId, newPrice);
        return ResponseEntity.ok("Stock price updated successfully.");
    }

    // Get details of a specific stock
    @GetMapping("/view/{stockId}")
    public ResponseEntity<Stock> getStockById(@PathVariable String stockId) throws StockNotFoundException {
        logger.info("Received request to fetch stock with ID: {}", stockId);
        Stock stock = stockService.getStockById(stockId);
        return ResponseEntity.ok(stock);
    }

    // Get all stocks
    @GetMapping("/view/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        logger.info("Received request to fetch all stocks.");
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

}
