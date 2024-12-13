package com.example.stock.price.service;

import com.example.stock.price.exceptions.StockNotFoundException;

public interface StockPriceService {
    double getStockPrice(String stockId) throws StockNotFoundException;
    void addStock(String stockId, double price);
    void updateStockPrice(String stockId, double price);
}
