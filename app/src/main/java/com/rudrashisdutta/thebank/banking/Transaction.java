package com.rudrashisdutta.thebank.banking;

import android.content.Context;

import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.database.Transactions;

public class Transaction {

    private String transactionID = null;
    private long customerID = -1;
    private long receiverID = -1;
    private long transactionTime = -1;
    private double amount = -1;

    private Transactions transactions;

    public String getTransactionID() {
        return transactionID;
    }

    private void setTransactionID(String transactionID) {
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

    public static Transaction build(String transactionID, long customerID, long receiverID, long transactionTime, double amount){
        Transaction transaction = new Transaction();
        transaction.setTransactionID(transactionID);
        transaction.setCustomerID(customerID);
        transaction.setReceiverID(receiverID);
        transaction.setTransactionTime(transactionTime);
        transaction.setAmount(amount);
        return transaction;
    }
    public static Transaction build(String transactionID, long customerID, long receiverID, double amount){
        Transaction transaction = new Transaction();
        transaction.setTransactionID(transactionID);
        transaction.setCustomerID(customerID);
        transaction.setReceiverID(receiverID);
        transaction.setAmount(amount);
        return transaction;
    }
    public static Transaction build(Context context, Transaction transaction, long transactionTime){
        String ID = generateTransactionID(transactionTime, Customers.get(context, transaction.getCustomerID()).getCustomerName(), Customers.get(context, transaction.getReceiverID()).getCustomerName());
        transaction.setTransactionID(ID);
        transaction.setTransactionTime(transactionTime);
        return transaction;
    }

    public static Transaction get(Context context, String transactionID){
        return Transactions.get(context, transactionID);
    }

    private static String generateTransactionID(long transactionTime, String senderName, String receiverName){
        StringBuilder ID = new StringBuilder();
        StringBuilder senderInitials = new StringBuilder();
        StringBuilder receiverInitials = new StringBuilder();
        for(String initial : senderName.split(" ")){
            try{
                senderInitials.append(initial.charAt(0));
            } catch (Exception ignore){}
        }
        for(String initial : receiverName.split(" ")){
            try{
                receiverInitials.append(initial.charAt(0));
            } catch (Exception ignore){}
        }
        ID.append(senderInitials).append(transactionTime).append(receiverInitials);
        return ID.toString();
    }
}
