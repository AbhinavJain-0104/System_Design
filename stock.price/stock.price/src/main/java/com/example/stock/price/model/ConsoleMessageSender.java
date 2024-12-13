package com.example.stock.price.model;

import org.springframework.stereotype.Component;

@Component  // This annotation will create a bean that Spring can inject

public class ConsoleMessageSender implements MessageSender{

    @Override
    public void send(String clientId, String stockId, double newPrice){
        System.out.println("Sending update to client " + clientId + ": " + stockId + " new price: " + newPrice);
    }
}
