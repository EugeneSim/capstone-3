package com.example.demooauth;

public class WithdrawResponse {
    
    private String message;
    private double newBalance;
    
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
    public WithdrawResponse(String message, double newBalance) {
        this.message = message;
        this.newBalance = newBalance;
    }

    
}
