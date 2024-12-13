package com.example.stock.price.model;


//This will help us abstract the concept of retrieving stock prices....like from an external api or mock implementation

public interface StockPriceProvider {
    double getStockPrice(String stockId);
}
