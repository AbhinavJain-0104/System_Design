package com.example.websocket.stockprice.service;

import com.example.websocket.stockprice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StockPriceUpdateService {
    private final StockPriceWebSocketHandler webSocketHandler;
    private final UserService userService;

    @Autowired
    public StockPriceUpdateService(StockPriceWebSocketHandler webSocketHandler, UserService userService) {
        this.webSocketHandler = webSocketHandler;
        this.userService = userService;
    }

    @Scheduled(fixedRate = 60000) // Fetch every minute
    public void fetchAndUpdateStockPrices() {
        for (User user : userService.getAllUsers().values()) {
            for (String stockSymbol : user.getSubscribedStocks()) {
                    double price = userService.fetchStockPrice(stockSymbol);
                    webSocketHandler.sendStockPriceUpdate(stockSymbol, price);
            }
        }
    }

//    or asynchronus stuff
//    @Scheduled(fixedRate = 60000) // Fetch every minute
//    public void fetchAndUpdateStockPrices() {
//        // Get all users
//        List<User> users = userService.getAllUsers().values().stream().collect(Collectors.toList());
//
//        // Process each user
//        for (User user : users) {
//            List<String> stocks = user.getSubscribedStocks();
//
//            // If the user has subscribed stocks, process them asynchronously
//            if (!stocks.isEmpty()) {
//                List<CompletableFuture<Void>> futures = stocks.stream()
//                        .map(stockSymbol -> CompletableFuture.runAsync(() -> {
//                            double price = userService.fetchStockPrice(stockSymbol);
//                            webSocketHandler.sendStockPriceUpdate(stockSymbol, price);
//                        }))
//                        .collect(Collectors.toList());
//
//                // Wait for all futures to complete
//                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//            }
//        }
//    }
}