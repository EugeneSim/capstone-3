package com.example.demooauth;

public class TransferRequest {
    private int senderAcctNum;
    private int receiverAcctNum;
    private double transferAmount;

    

    public TransferRequest(int senderAcctNum, int receiverAcctNum, double transferAmount) {
        this.senderAcctNum = senderAcctNum;
        this.receiverAcctNum = receiverAcctNum;
        this.transferAmount = transferAmount;
    }



    public int getSenderAcctNum() {
        return senderAcctNum;
    }



    public void setSenderAcctNum(int senderAcctNum) {
        this.senderAcctNum = senderAcctNum;
    }



    public int getReceiverAcctNum() {
        return receiverAcctNum;
    }



    public void setReceiverAcctNum(int receiverAcctNum) {
        this.receiverAcctNum = receiverAcctNum;
    }



    public double getTransferAmount() {
        return transferAmount;
    }



    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }



    @Override
    public String toString() {
        return "TransferRequest{" +
                "senderAcctNum=" + senderAcctNum +
                ", receiverAcctNum=" + receiverAcctNum +
                ", transferAmount=" + transferAmount +
                '}';
    }
}

