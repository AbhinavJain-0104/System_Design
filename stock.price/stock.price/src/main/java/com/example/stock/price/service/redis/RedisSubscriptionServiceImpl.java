package com.example.stock.price.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisSubscriptionServiceImpl implements RedisSubscriptionService {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String SUBSCRIBERS_PREFIX = "subscribers:";

    public RedisSubscriptionServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void subscribeClient(String stockId, String clientId) {
        redisTemplate.opsForSet().add(SUBSCRIBERS_PREFIX + stockId, clientId);
    }

    @Override
    public void unsubscribeClient(String stockId, String clientId) {
        redisTemplate.opsForSet().remove(SUBSCRIBERS_PREFIX + stockId, clientId);
    }

    @Override
    public Set<String> getSubscribers(String stockId) {
        return redisTemplate.opsForSet().members(SUBSCRIBERS_PREFIX + stockId);
    }

    @Override
    public Long getSubscriberCount(String stockId) {
        return redisTemplate.opsForSet().size(SUBSCRIBERS_PREFIX + stockId);
    }
}