package com.example.stock.price.service;

import com.example.stock.price.model.ClientSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemorySubscriptionManager implements SubscriptionManager{

    // Map to hold stockId and the list of subscribed clients
    private final Map<String, List<ClientSession>> subscriptions = new HashMap<>();

    @Override
    public void subscribeClient(String stockId, ClientSession client) {
        // Check if the stockId already has a list of clients
        List<ClientSession> clients = subscriptions.get(stockId);
        if (clients == null) {
            // If not, create a new list for this stockId
            clients = new ArrayList<>();
            subscriptions.put(stockId, clients);
        }
        // Add the client to the list
        clients.add(client);
        System.out.println("Client " + client.getClientId() + " subscribed to stock: " + stockId);
    }

    @Override
    public void notifyClients(String stockId, double newPrice) {
        // Notify all subscribed clients about the new price
        List<ClientSession> clients = subscriptions.get(stockId);
        if (clients != null) {
            for (ClientSession client : clients) {
                client.sendPriceUpdate(stockId, newPrice);
            }
        }
    }
}
