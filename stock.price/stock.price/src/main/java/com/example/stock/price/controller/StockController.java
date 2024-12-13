package com.example.stock.price.controller;

import com.example.stock.price.exceptions.StockNotFoundException;
import com.example.stock.price.model.ClientSession;
import com.example.stock.price.model.ConsoleMessageSender;
import com.example.stock.price.model.StockRequest;
import com.example.stock.price.model.SubscriptionRequest;
import com.example.stock.price.service.InMemorySubscriptionManager;
import com.example.stock.price.service.StockPriceSimulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks/memory")
public class StockController {

    private final StockPriceSimulator stockPriceSimulator;
    private final InMemorySubscriptionManager subscriptionManager;

    @Autowired
    public StockController(InMemorySubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
        this.stockPriceSimulator = new StockPriceSimulator(subscriptionManager);
        this.stockPriceSimulator.startSimulatingStockPrices(); // Start simulation on initialization
    }

    //{
    //    "stockId": "ABHINAV",
    //    "initialPrice": 1500.00
    //}

    @PostMapping("/add")
    public String addStock(@RequestBody StockRequest stockRequest) {
        stockPriceSimulator.addStock(stockRequest.getStockId(), stockRequest.getInitialPrice());
        return "Stock " + stockRequest.getStockId() + " added with initial price " + stockRequest.getInitialPrice();
    }



    //{
    //    "stockId": "ABHINAV",
    //    "clientId": "Client2"
    //}


    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeClient(@RequestBody SubscriptionRequest subscriptionRequest) {
        // Check if the stock exists using the new method
        if (!stockPriceSimulator.stockExists(subscriptionRequest.getStockId())) {
            throw new StockNotFoundException("Stock " + subscriptionRequest.getStockId() + " does not exist.");
        }

        // Create a new client session
        ClientSession client = new ClientSession(subscriptionRequest.getClientId(), new ConsoleMessageSender());

        // Subscribe the client
        subscriptionManager.subscribeClient(subscriptionRequest.getStockId(), client);
        return ResponseEntity.ok("Client " + subscriptionRequest.getClientId() + " subscribed to stock " + subscriptionRequest.getStockId());
    }


}