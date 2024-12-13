package com.example.stock.price.service;
import com.example.stock.price.service.redis.RedisNotificationService;
import com.example.stock.price.service.redis.RedisSubscriptionService;

import com.example.stock.price.model.ClientSession;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisSubscriptionManager implements SubscriptionManager {
    private final RedisSubscriptionService redisSubscriptionService;
    private final RedisNotificationService notificationService;

    public RedisSubscriptionManager(
            RedisSubscriptionService redisSubscriptionService,
            RedisNotificationService notificationService
    ) {
        this.redisSubscriptionService = redisSubscriptionService;
        this.notificationService = notificationService;
    }

    @Override
    public void subscribeClient(String stockId, ClientSession client) {
        redisSubscriptionService.subscribeClient(stockId, client.getClientId());
    }

    @Override
    public void notifyClients(String stockId, double newPrice) {
        notificationService.notifySubscribers(stockId, newPrice);
    }

}