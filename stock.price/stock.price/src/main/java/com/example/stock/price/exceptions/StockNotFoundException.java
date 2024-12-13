package com.example.stock.price.exceptions;

public class StockNotFoundException extends RuntimeException{
    public StockNotFoundException(String stockId) {
        super("Stock not found: " + stockId);
    }
}
