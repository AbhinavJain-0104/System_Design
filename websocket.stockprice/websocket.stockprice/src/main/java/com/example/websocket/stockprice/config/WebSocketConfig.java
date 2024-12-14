package com.example.websocket.stockprice.config;

import com.example.websocket.stockprice.service.StockPriceWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**Inside this configuration class, you define the endpoint (like /stock-price).
 * This is the URL that clients will use to connect to your WebSocket server.
 For example, if you set the endpoint to /stock-price, clients will connect using ws://localhost:8080/stock-price
 * */


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final StockPriceWebSocketHandler stockPriceWebSocketHandler;

    @Autowired
    public WebSocketConfig(StockPriceWebSocketHandler stockPriceWebSocketHandler) {
        this.stockPriceWebSocketHandler = stockPriceWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(stockPriceWebSocketHandler, "/stock-price")
                .setAllowedOrigins("*"); // Allow all origins (adjust as needed)
    }
}