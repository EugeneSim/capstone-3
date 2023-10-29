package com.example.demooauth;

public class DepositRequest {
    private int acctNum; 
    private double depositAmount;

    public int getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public DepositRequest(int acctNum, double depositAmount) {
        this.acctNum = acctNum;
        this.depositAmount = depositAmount;
    }
}
