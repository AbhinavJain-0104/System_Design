package com.example.stockpricerandom.service;


import com.example.stockpricerandom.exceptions.StockNotFoundException;
import com.example.stockpricerandom.exceptions.UserNotFoundException;
import com.example.stockpricerandom.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {
    // In-memory storage for users, with userId as the key
    private final Map<String, User> users = new HashMap<>();

    /**
     * Creates a new user.
     *
     * @param userId   The unique identifier for the user.
     * @param userName The name of the user.
     */
    public void createUser(String userId, String userName) {
        if (users.containsKey(userId)) {
            throw new IllegalArgumentException("User with ID " + userId + " already exists.");
        }
        users.put(userId, new User(userId, userName));
    }

    /**
     * Deletes an existing user.
     *
     * @param userId The unique identifier of the user to delete.
     */
    public void deleteUser(String userId) throws UserNotFoundException {
        if (!users.containsKey(userId)) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        users.remove(userId);
    }

    /**
     * Subscribes a user to a stock.
     *
     * @param userId   The unique identifier of the user.
     * @param stockId  The unique identifier of the stock.
     */
    public void subscribeToStock(String userId, String stockId, StockService stockService) throws StockNotFoundException, UserNotFoundException {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        // Validate the stock exists in StockService
        if (stockService.getAllTrackedStocks().stream().noneMatch(stock -> stock.getStockId().equals(stockId))) {
            throw new StockNotFoundException("Stock with ID " + stockId + " not found.");
        }

        // Add the stockId to the user's subscriptions
        Set<String> subscribedStocks = user.getSubscribedStocks();
        if (!subscribedStocks.add(stockId)) {
            throw new IllegalArgumentException("User is already subscribed to stock with ID " + stockId);
        }
    }

    /**
     * Unsubscribes a user from a stock.
     *
     * @param userId   The unique identifier of the user.
     * @param stockId  The unique identifier of the stock.
     */
    public void unsubscribeFromStock(String userId, String stockId) throws UserNotFoundException {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }

        Set<String> subscribedStocks = user.getSubscribedStocks();
        if (!subscribedStocks.remove(stockId)) {
            throw new IllegalArgumentException("User is not subscribed to stock with ID " + stockId);
        }
    }

    /**
     * Fetches all stocks a user is subscribed to.
     *
     * @param userId The unique identifier of the user.
     * @return A set of subscribed stock IDs.
     */
    public Set<String> getUserSubscriptions(String userId) throws UserNotFoundException {
        User user = users.get(userId);
        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found.");
        }
        return new HashSet<>(user.getSubscribedStocks()); // Return a copy to avoid modification
    }
}