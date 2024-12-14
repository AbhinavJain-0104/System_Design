package com.example.websocket.stockprice.controller;

import com.example.websocket.stockprice.model.User;
import com.example.websocket.stockprice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    // Creating a logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody User user) {
        // Create user with username and email
        User createdUser = userService.createUser(user.getUsername());

        // Log successful registration
        logger.info("User Registered successfully");

        // Create the response map
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User Registered successfully");
        response.put("user", createdUser);

        // Return the response with HTTP status 200 OK
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}/subscribe")
    public ResponseEntity<String> subscribe(@PathVariable String userId, @RequestParam String stockSymbol) {
        logger.info("Inside UserController.subscribe()");
        userService.addSubscription(userId, stockSymbol);
        return ResponseEntity.ok("Subscribed to stock: " + stockSymbol);
    }

    @DeleteMapping("/{userId}/unsubscribe")
    public ResponseEntity<String> unsubscribe(@PathVariable String userId, @RequestParam String stockSymbol) {
        logger.info("Inside UserController.unsubscribe()");
        userService.removeSubscription(userId, stockSymbol);
        return ResponseEntity.ok("Unsubscribed from stock: " + stockSymbol);
    }

    @GetMapping("/{userId}/stock-price")
    public ResponseEntity<String> getStockPrice(@PathVariable String userId, @RequestParam String stockSymbol) {
        double price = userService.fetchStockPrice(stockSymbol);
        return ResponseEntity.ok("Current price of " + stockSymbol + ": " + price);
    }


    @GetMapping("/{userId}/stocks")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
}