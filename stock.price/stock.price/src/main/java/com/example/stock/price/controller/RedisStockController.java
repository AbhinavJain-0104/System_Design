package com.example.stock.price.controller;

import com.example.stock.price.exceptions.StockNotFoundException;
import com.example.stock.price.model.StockRequest;
import com.example.stock.price.model.SubscriptionRequest;
import com.example.stock.price.service.redis.RedisStockPriceSimulator;
import com.example.stock.price.service.redis.RedisSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks/redis")
public class RedisStockController {
    private final RedisStockPriceSimulator redisStockPriceSimulator;
    private final RedisSubscriptionService redisSubscriptionService;

    public RedisStockController(
        RedisStockPriceSimulator redisStockPriceSimulator,
        RedisSubscriptionService redisSubscriptionService
    ) {
        this.redisStockPriceSimulator = redisStockPriceSimulator;
        this.redisSubscriptionService = redisSubscriptionService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStock(@RequestBody StockRequest stockRequest) {
        redisStockPriceSimulator.addStock(
            stockRequest.getStockId(), 
            stockRequest.getInitialPrice()
        );
        
        return ResponseEntity.ok(
            "Redis: Stock " + stockRequest.getStockId() + 
            " added with initial price " + stockRequest.getInitialPrice()
        );
    }

    @GetMapping("/price/{stockId}")
    public ResponseEntity<?> getStockPrice(@PathVariable String stockId) {
        try {
            double price = redisStockPriceSimulator.getStockPrice(stockId);
            return ResponseEntity.ok(price);
        } catch (StockNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToStock(@RequestBody SubscriptionRequest subscriptionRequest) {
        // Validate stock existence
        if (!redisStockPriceSimulator.stockExists(subscriptionRequest.getStockId())) {
            return ResponseEntity.badRequest().body("Stock not found in Redis");
        }

        // Subscribe client
        redisSubscriptionService.subscribeClient(
            subscriptionRequest.getStockId(), 
            subscriptionRequest.getClientId()
        );

        return ResponseEntity.ok(
            "Redis: Client " + subscriptionRequest.getClientId() + 
            " subscribed to " + subscriptionRequest.getStockId()
        );
    }

    @GetMapping("/subscribers/{stockId}")
    public ResponseEntity<Long> getSubscriberCount(@PathVariable String stockId) {
        Long count = redisSubscriptionService.getSubscriberCount(stockId);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeFromStock(@RequestBody SubscriptionRequest subscriptionRequest) {
        redisSubscriptionService.unsubscribeClient(
            subscriptionRequest.getStockId(), 
            subscriptionRequest.getClientId()
        );

        return ResponseEntity.ok(
            "Redis: Client " + subscriptionRequest.getClientId() + 
            " unsubscribed from " + subscriptionRequest.getStockId()
        );
    }
}