package com.example.portfoliomanagement.model;


/*There can be three scenarios when we need to update our profit loss ->
* when the quantity changes-> when user sells
* when the stock price changes
* when we buy more quantity of stocks of some different price
*  **/

public class PortfolioEntry {
    private String stockId; // Unique ID of the stock
    private String stockName; // Name of the stock
    private int quantity; // Number of shares owned
    private double averagePurchasePrice; // Average price at which the stock was bought
    private double currentPrice; // Latest price of the stock
    private double profitOrLoss; // Calculated profit or loss for this stock

    // Constructor
    public PortfolioEntry(String stockId, String stockName, int quantity, double averagePurchasePrice, double currentPrice) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.quantity = quantity;
        this.averagePurchasePrice = averagePurchasePrice;
        this.currentPrice = currentPrice;
        this.profitOrLoss = calculateProfitOrLoss(); // Calculate on initialization
    }




    // Getters and Setters
    public String getStockId() {
        return stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAveragePurchasePrice() {
        return averagePurchasePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getProfitOrLoss() {
        return profitOrLoss;
    }


    // Method to calculate profit or loss

    private double calculateProfitOrLoss() {
        return (currentPrice - averagePurchasePrice) * quantity;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.profitOrLoss = calculateProfitOrLoss(); // Recalculate when quantity changes
    }

    public void setAveragePurchasePrice(double averagePurchasePrice) {
        this.averagePurchasePrice = averagePurchasePrice;
        this.profitOrLoss = calculateProfitOrLoss(); // Recalculate when purchase price changes
    }


    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
        this.profitOrLoss = calculateProfitOrLoss(); // Recalculate when current price changes
    }


}
