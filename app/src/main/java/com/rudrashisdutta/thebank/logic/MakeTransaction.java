package com.rudrashisdutta.thebank.logic;

import android.content.Context;
import android.util.Log;

import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.database.Customers;
import com.rudrashisdutta.thebank.database.Transactions;

import java.math.BigDecimal;
import java.util.Date;

public class MakeTransaction {

    private Context context;

    private Transaction transaction;
    private long transactionTime;

    private long getTransactionTime() {
        return transactionTime;
    }

    private void setTransactionTime(long transactionTime) {
        this.transactionTime = transactionTime;
    }

    private Transaction getTransaction() {
        return transaction;
    }
    private boolean updateBalance(long senderID, long receiverID){
        boolean success = false;
        try {
            Customers customers = new Customers(context);
            Customer sender = Customers.get(context, senderID);
            Customer receiver = Customers.get(context, receiverID);
            double senderNewBalance = sender.getBalance() - getTransaction().getAmount();
            senderNewBalance = new BigDecimal(senderNewBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double receiverNewBalance = receiver.getBalance() + getTransaction().getAmount();
            receiverNewBalance = new BigDecimal(receiverNewBalance).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            customers.updateBalance(sender, senderNewBalance);
            customers.updateBalance(receiver, receiverNewBalance);
            success = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    public Transaction make(){
        try {
            Transactions transactions = new Transactions(context);
            Date dateTimeNow = new Date();
            Transaction.build(context, transaction, dateTimeNow.getTime());
            boolean transactionSuccessful = transactions.store(getTransaction());
            if(transactionSuccessful){
                updateBalance(getTransaction().getCustomerID(), getTransaction().getReceiverID());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return getTransaction();
    }

    private void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


    public static MakeTransaction build(Customer sender, Customer receiver, double amount, Context context){
        MakeTransaction makeTransaction = new MakeTransaction();
        Transaction transaction = Transaction.build("", sender.getCustomerID(), receiver.getCustomerID(), 0, amount);
        makeTransaction.setTransaction(transaction);
        makeTransaction.context = context;
        return makeTransaction;
    }
    public static boolean checkIfPossible(Customer sender, double amount){
        boolean possible;
        possible = !(amount > sender.getBalance());
        return possible;
    }

}
