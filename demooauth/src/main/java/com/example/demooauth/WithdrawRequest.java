package com.example.demooauth;

public class WithdrawRequest {
    private int acctNum; 
    private double withdrawAmount;
    

    public int getAcctNum() {
        return acctNum;
    }


    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }


    public double getWithdrawAmount() {
        return withdrawAmount;
    }


    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }


    public WithdrawRequest(int acctNum, double withdrawAmount) {
        this.acctNum = acctNum;
        this.withdrawAmount = withdrawAmount;
    }

    
}
