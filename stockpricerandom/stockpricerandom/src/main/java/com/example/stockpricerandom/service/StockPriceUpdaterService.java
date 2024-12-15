package com.example.stockpricerandom.service;

import com.example.stockpricerandom.exceptions.StockNotFoundException;
import com.example.stockpricerandom.model.Stock;
import com.example.stockpricerandom.observer.ConsoleNotificationService;
import com.example.stockpricerandom.observer.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class StockPriceUpdaterService {

    @Autowired
    private StockService stockService;
    @Autowired
ConsoleNotificationService consoleNotificationService;
    /**
     * har thread pool jaise 4 multiple threads with 10 stocks each alag alag execute hogi
     * */
    // Thread pool size (configurable based on system capacity)
    private static final int THREAD_POOL_SIZE = 4;

    // Batch size for splitting stocks
    private static final int BATCH_SIZE = 10;

    /**
     * Simulates price updates for all tracked stocks every 30 seconds.
     */
    @Scheduled(fixedRate = 30000) // Executes every 30 seconds
    public void simulatePriceUpdates() throws StockNotFoundException {
        // Fetch all tracked stocks
        List<Stock> allStocks = stockService.getAllTrackedStocks();

        for (Stock stock : allStocks) {
            double currentPrice = stock.getCurrentPrice();

            // Randomly fluctuate price by ±5%
            double priceChange = currentPrice * (Math.random() * 0.1 - 0.05); // Random % between -5% and +5%
            double newPrice = Math.max(1.0, currentPrice + priceChange); // Ensure price is >= 1.0

            // Update the price
            stockService.updatePrice(stock.getStockId(), newPrice,consoleNotificationService);
        }
    }




//    /**
//     * Simulates price updates for all tracked stocks using multithreading.
//     * Scheduled to run every 30 seconds.
//     */
//    @Scheduled(fixedRate = 30000) // Executes every 30 seconds
//    public void simulatePriceUpdates() {
//        // Fetch all tracked stocks
//        List<Stock> allStocks = stockService.getAllTrackedStocks();
//
//        // Split the stocks into batches
//        List<List<Stock>> batches = splitIntoBatches(allStocks, BATCH_SIZE);
//
//        // Create a fixed thread pool
//        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//
//        // Process each batch in a separate thread
//        for (List<Stock> batch : batches) {
//            executorService.submit(() -> {
//                try {
//                    processBatch(batch);
//                } catch (StockNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        }
//
//        // Shutdown the executor service gracefully
//        executorService.shutdown();
//    }
//
//
//    /**
//     * Processes a batch of stocks and updates their prices.
//     *
//     * @param batch The batch of stocks to process.
//     */
//    private void processBatch(List<Stock> batch) throws StockNotFoundException {
//        for (Stock stock : batch) {
//            double currentPrice = stock.getCurrentPrice();
//
//            // Randomly fluctuate price by ±5%
//            double priceChange = currentPrice * (Math.random() * 0.1 - 0.05); // Random % between -5% and +5%
//            double newPrice = Math.max(1.0, currentPrice + priceChange); // Ensure price is >= 1.0
//
//            // Update the price
//            stockService.updatePrice(stock.getStockId(), newPrice);
//        }
//    }
//
//    /**
//     * Splits a list of stocks into smaller batches.
//     *
//     * @param stocks The list of stocks to split.
//     * @param batchSize The maximum size of each batch.
//     * @return A list of batches (each batch is a list of stocks).
//     */
//    private List<List<Stock>> splitIntoBatches(List<Stock> stocks, int batchSize) {
//        List<List<Stock>> batches = new ArrayList<>();
//        for (int i = 0; i < stocks.size(); i += batchSize) {
//            int end = Math.min(i + batchSize, stocks.size());
//            batches.add(stocks.subList(i, end));
//        }
//        return batches;
//    }
//}

}