package com.example.stockpricerandom.controller;

import com.example.stockpricerandom.exceptions.StockNotFoundException;
import com.example.stockpricerandom.exceptions.UserNotFoundException;
import com.example.stockpricerandom.service.StockService;
import com.example.stockpricerandom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam String userId, @RequestParam String userName) {
        userService.createUser(userId, userName);
        return ResponseEntity.ok("User created successfully.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) throws UserNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @PostMapping("/stocks/{userId}/{stockId}")
    public ResponseEntity<String> subscribeToStock(@PathVariable String userId, @PathVariable String stockId) throws UserNotFoundException, StockNotFoundException {
        userService.subscribeToStock(userId, stockId, stockService);
        return ResponseEntity.ok("User subscribed to stock successfully.");
    }

    @DeleteMapping("/{userId}/stocks/{stockId}")
    public ResponseEntity<String> unsubscribeFromStock(@PathVariable String userId, @PathVariable String stockId) throws UserNotFoundException {
        userService.unsubscribeFromStock(userId, stockId);
        return ResponseEntity.ok("User unsubscribed from stock successfully.");
    }

    @GetMapping("/{userId}/stocks")
    public ResponseEntity<Set<String>> getUserSubscriptions(@PathVariable String userId) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserSubscriptions(userId));
    }
}