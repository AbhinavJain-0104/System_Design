package com.example.stock.price.service;

import com.example.stock.price.model.ClientSession;

public interface SubscriptionManager {
        void subscribeClient(String stockId, ClientSession client);
        void notifyClients(String stockId, double newPrice);
}
