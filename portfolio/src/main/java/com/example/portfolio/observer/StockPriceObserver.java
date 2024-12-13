package com.example.portfolio.observer;

/**
 * Observer interface for receiving stock price updates and deletions.
 */
public interface StockPriceObserver {
    void updateStockPrice(String stockId, double newPrice);
    void deleteStock(String stockId); // New method for stock deletion notifications
}