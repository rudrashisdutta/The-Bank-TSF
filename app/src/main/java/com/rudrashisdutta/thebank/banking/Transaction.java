package com.rudrashisdutta.thebank.banking;

import com.rudrashisdutta.thebank.database.Transactions;

public class Transaction {

    private long transactionID = -1;
    private long customerID = -1;
    private long receiverID = -1;
    private long transactionTime = -1;
    private double amount = -1;

    private Transactions transactions;

    public long getTransactionID() {
        return transactionID;
    }

    private void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public long getCustomerID() {
        return customerID;
    }

    private void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public long getReceiverID() {
        return receiverID;
    }

    private void setReceiverID(long receiverID) {
        this.receiverID = receiverID;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    private void setTransactionTime(long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getAmount() {
        return amount;
    }

    private void setAmount(double amount) {
        this.amount = amount;
    }

    public static Transaction create(long transactionID, long customerID, long receiverID, long transactionTime, double amount){
        Transaction transaction = new Transaction();
        transaction.setTransactionID(transactionID);
        transaction.setCustomerID(customerID);
        transaction.setReceiverID(receiverID);
        transaction.setTransactionTime(transactionTime);
        transaction.setAmount(amount);
        return transaction;
    }

    public static Transaction get(long transactionID){
        return null;
    }
}
