package com.example.websocket.stockprice.service;

import com.example.websocket.stockprice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class StockPriceWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final UserService userService; // Inject UserService to manage subscriptions

    @Autowired
    public StockPriceWebSocketHandler(UserService userService) {
        this.userService = userService; // Initialize UserService
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session); // Add the new session to the set
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Remove the WebSocket session when the connection is closed
        sessions.remove(session);
    }


    @Override
public void handleTextMessage(WebSocketSession session, TextMessage message) {
    String payload = message.getPayload(); // Get the text message content

    // Example message formats:
    // 1. "subscribe:AAPL:userID"
    // 2. "subscribe:COINBASE:BTC-USD:userID"
    if (payload.startsWith("subscribe:")) {
        String[] parts = payload.split(":");
        
        // Check if the message has at least 3 parts (user ID and at least one stock symbol)
        if (parts.length >= 3) {
            String userId = parts[parts.length - 1].trim(); // Last part is always the user ID
            String stockSymbol = String.join(":", Arrays.copyOfRange(parts, 1, parts.length - 1)); // Join all parts except the first and last for the stock symbol

            try {
               User user= userService.getUser(userId);
                if (!user.getSubscribedStocks().contains(stockSymbol)) {
                    userService.addSubscription(userId, stockSymbol); // Subscribe user to stock
                }
                // Fetch the stock price and send it to the client
                double price = userService.fetchStockPrice(stockSymbol);
                sendStockPriceUpdate(stockSymbol, price); // Send the initial price update
            } catch (IllegalArgumentException e) {
                System.out.println("Error subscribing user: " + e.getMessage());
                // Optionally send an error message back to the client
            }
        } else {
            System.out.println("Invalid subscription format: " + payload);
        }
    }
}

    public void sendStockPriceUpdate(String stockSymbol, double price) {
        String message = "Stock: " + stockSymbol + " Price: " + price;
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message)); // Send the message to all sessions
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


