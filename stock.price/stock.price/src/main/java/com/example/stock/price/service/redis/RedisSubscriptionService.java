package com.example.stock.price.service.redis;

import java.util.Set;

public interface RedisSubscriptionService {
    void subscribeClient(String stockId, String clientId);
    void unsubscribeClient(String stockId, String clientId);
    Set<String> getSubscribers(String stockId);
    Long getSubscriberCount(String stockId);
}