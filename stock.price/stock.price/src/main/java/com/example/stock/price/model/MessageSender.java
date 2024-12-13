package com.example.stock.price.model;

public interface MessageSender {
    public void send(String clientId, String stockId, double newPrice);

}
