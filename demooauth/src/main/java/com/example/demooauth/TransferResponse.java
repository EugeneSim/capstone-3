package com.example.demooauth;

public class TransferResponse {
    private String message;
    private double newBalance;
    
    

    public TransferResponse(String message, double newBalance) {
        this.message = message;
        this.newBalance = newBalance;
    }



    public String getMessage() {
        return message;
    }



    public void setMessage(String message) {
        this.message = message;
    }



    public double getNewBalance() {
        return newBalance;
    }



    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }



    @Override
    public String toString() {
        return "TransferResponse{" +
                "message='" + message + '\'' +
                ", newBalance=" + newBalance +
                '}';
    }
}
