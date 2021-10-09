package com.rudrashisdutta.thebank.logic;

import android.content.Context;

import com.rudrashisdutta.thebank.banking.Customer;
import com.rudrashisdutta.thebank.banking.Transaction;
import com.rudrashisdutta.thebank.database.Transactions;

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

    public Transaction make(){
        Transactions transactions = new Transactions(context);
        return transactions.store(getTransaction()) ? getTransaction() : null;
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

}
