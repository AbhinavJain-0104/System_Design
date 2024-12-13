package com.example.stock.price.service.redis;

import com.example.stock.price.model.MessageSender;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisNotificationService {
    private final RedisSubscriptionService subscriptionService;
    private final MessageSender messageSender;

    public RedisNotificationService(
            RedisSubscriptionService subscriptionService,
            MessageSender messageSender
    ) {
        this.subscriptionService = subscriptionService;
        this.messageSender = messageSender;
    }

    public void notifySubscribers(String stockId, double newPrice) {
        // Retrieve subscribers
        Set<String> subscribers = subscriptionService.getSubscribers(stockId);

        // Send personalized notifications
        if (subscribers != null) {
            subscribers.forEach(clientId ->
                    messageSender.send(clientId, stockId, newPrice)
            );
        }
    }
}