package com.example.stock.price.service.redis;

import com.example.stock.price.exceptions.StockNotFoundException;
import com.example.stock.price.model.StockPriceProvider;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class RedisStockPriceSimulator implements StockPriceProvider {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisNotificationService redisNotificationService;
    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutorService;
    private final Random random = new Random();

    private static final String STOCK_PRICE_PREFIX = "stock:price:";
    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    public RedisStockPriceSimulator(
        RedisTemplate<String, String> redisTemplate,
        RedisNotificationService redisNotificationService
    ) {
        this.redisTemplate = redisTemplate;
        this.redisNotificationService = redisNotificationService;
        
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
        
        startPriceSimulation();
    }

    @Override
    public double getStockPrice(String stockId) throws StockNotFoundException {
        String priceStr = redisTemplate.opsForValue().get(STOCK_PRICE_PREFIX + stockId);
        if (priceStr == null) {
            throw new StockNotFoundException("Stock not found: " + stockId);
        }
        return Double.parseDouble(priceStr);
    }

    public void addStock(String stockId, double initialPrice) {
        redisTemplate.opsForValue().set(STOCK_PRICE_PREFIX + stockId, String.valueOf(initialPrice));
    }

    private void startPriceSimulation() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            try {
                List<String> stockKeys = redisTemplate.keys(STOCK_PRICE_PREFIX + "*")
                    .stream()
                    .collect(Collectors.toList());

                CountDownLatch latch = new CountDownLatch(stockKeys.size());

                stockKeys.forEach(key -> 
                    executorService.submit(() -> {
                        try {
                            updateStockPrice(key);
                        } finally {
                            latch.countDown();
                        }
                    })
                );

                latch.await(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                System.err.println("Stock price simulation error: " + e.getMessage());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void updateStockPrice(String key) {
        try {
            redisTemplate.execute((RedisCallback<Void>) connection -> {
                try {
                    byte[] priceBytes = connection.get(key.getBytes());
                    if (priceBytes == null) return null;

                    double currentPrice = Double.parseDouble(new String(priceBytes));
                    double newPrice = calculateNewPrice(currentPrice);
                    
                    connection.set(key.getBytes(), String.valueOf(newPrice).getBytes());
                    
                    // Use RedisNotificationService for notifications
                    redisNotificationService.notifySubscribers(
                        extractStockId(key), 
                        newPrice
                    );
                } catch (Exception e) {
                    System.err.println("Error updating stock price: " + e.getMessage());
                }
                return null;
            });
        } catch (Exception e) {
            System.err.println("Redis transaction error: " + e.getMessage());
        }
    }

    private double calculateNewPrice(double currentPrice) {
        double change = (random.nextDouble() - 0.5) * 0.05 * currentPrice;
        return currentPrice + change;
    }

    private String extractStockId(String key) {
        return key.replace(STOCK_PRICE_PREFIX, "");
    }

    public boolean stockExists(String stockId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(STOCK_PRICE_PREFIX + stockId));
    }
}