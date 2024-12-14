package com.example.websocket.stockprice.service;

import com.example.websocket.stockprice.model.FinnhubResponse;
import com.example.websocket.stockprice.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();
//    private final String API_KEY = "IU1YMES4E1UFLDI2"; // Your Alpha Vantage API key
//    private final String BASE_URL = "https://www.alphavantage.co/query";

    private final String API_KEY = "cteia59r01qt478m1030cteia59r01qt478m103g"; // Your Finnub API key
    private final String BASE_URL = "https://finnhub.io/api/v1/quote";
    private final RestTemplate restTemplate = new RestTemplate();

    public User createUser(String username) {
        if(users.containsKey(username)){
            throw new IllegalArgumentException("user already exists for name: "+username);
        }
        User user = new User(username);
        users.put(username, user);
        return user;
    }

    public void addSubscription(String userId, String stockSymbol) {
        if (isValidStockSymbol(stockSymbol)) {
            User user = users.get(userId);
            if (user != null) {
                // Check if the user is already subscribed to the stock
                if (user.getSubscribedStocks().contains(stockSymbol)) {
                    throw new IllegalArgumentException("User is already subscribed to stock: " + stockSymbol);
                }
                user.addSubscribedStock(stockSymbol); // Add the stock if valid and not already subscribed
            } else {
                throw new IllegalArgumentException("User not found: " + userId);
            }
        } else {
            throw new IllegalArgumentException("Invalid stock symbol: " + stockSymbol);
        }
    }

    public void removeSubscription(String userId, String stockSymbol) {
        User user = users.get(userId);
        if (user != null) {
            // Check if the stock symbol exists in the user's subscriptions
            if (!user.getSubscribedStocks().contains(stockSymbol)) {
                throw new IllegalArgumentException("User is not subscribed to stock: " + stockSymbol);
            }
            user.removeSubscribedStock(stockSymbol); // Remove the stock if it exists
        } else {
            throw new IllegalArgumentException("User not found: " + userId);
        }
    }
    public User getUser(String userId) {
        return users.get(userId);
    }


    private boolean isValidStockSymbol(String stockSymbol) {
        String url = UriComponentsBuilder.fromHttpUrl("https://finnhub.io/api/v1/quote")
                .queryParam("symbol", stockSymbol)
                .queryParam("token", API_KEY)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        FinnhubResponse response;

        try {
            response = restTemplate.getForObject(url, FinnhubResponse.class);
        } catch (Exception e) {
            // Handle the exception (e.g., log it)
            return false; // Return false if there's an error
        }

        // Check if the response is valid
        return response != null && response.getCurrentPrice() > 0; // Valid if price is greater than 0
    }



    public double fetchStockPrice(String stockSymbol) {
        // Build the URL for the Finnhub API
        String url = UriComponentsBuilder.fromHttpUrl("https://finnhub.io/api/v1/quote")
                .queryParam("symbol", stockSymbol)
                .queryParam("token", API_KEY)
                .toUriString();

        // Make the API call and get the response
        FinnhubResponse response;
        try {
            response = restTemplate.getForObject(url, FinnhubResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching stock price: " + e.getMessage());
        }

        // Validate the response
        if (response == null) {
            throw new RuntimeException("Received null response from Finnhub API");
        }
        return response.getCurrentPrice(); // Return the valid current price
    }

    public Map<String, User> getAllUsers() {
        return users; // Return the map of users
    }






//    private boolean isValidStockSymbol(String stockSymbol) {
//        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
//                .queryParam("function", "SYMBOL_SEARCH")
//                .queryParam("keywords", stockSymbol)
//                .queryParam("apikey", API_KEY)
//                .toUriString();
//
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
//
//        // Check if the response contains valid stock data
//        if (response != null && response.containsKey("bestMatches")) {
//            List<?> bestMatches = (List<?>) response.get("bestMatches");
//            return bestMatches != null && !bestMatches.isEmpty();
//        }
//        return false;
//    }






    // Fetch live stock price from Alpha Vantage
//    public double fetchStockPrice(String stockSymbol) {
//        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
//                .queryParam("function", "TIME_SERIES_INTRADAY")
//                .queryParam("symbol", stockSymbol)
//                .queryParam("interval", "1min")
//                .queryParam("apikey", API_KEY)
//                .toUriString();
//
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
//
//        // Extract the latest price from the response
//        if (response != null && response.containsKey("Time Series (1min)")) {
//            Map<String, Object> timeSeries = (Map<String, Object>) response.get("Time Series (1min)");
//            if (!timeSeries.isEmpty()) {
//                // Get the latest entry
//                String latestTime = timeSeries.keySet().iterator().next();
//                Map<String, String> latestData = (Map<String, String>) timeSeries.get(latestTime);
//                return Double.parseDouble(latestData.get("1. open")); // Return the opening price
//            }
//        }
//        return 0.0; // Return 0 if unable to fetch price
//    }



}