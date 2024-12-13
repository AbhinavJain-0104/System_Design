package com.example.stock.price.model;

public class ClientSession {
    private String clientId;
    private MessageSender messageSender;

    public ClientSession(String clientId, MessageSender messageSender) {
        this.clientId = clientId;
        this.messageSender = messageSender;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MessageSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }



    public void sendPriceUpdate(String stockId, double newPrice) {
        messageSender.send(clientId, stockId, newPrice);
    }

}
