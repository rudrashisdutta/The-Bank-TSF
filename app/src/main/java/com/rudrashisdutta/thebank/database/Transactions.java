package com.rudrashisdutta.thebank.database;

import android.content.Context;

import com.rudrashisdutta.thebank.banking.Transaction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Transactions extends Database{
    private static String DB_NAME = "BANKING";
    private static int DB_VER = 1;

    private static String TABLE = "TRANSACTIONS";
    private static LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>(){
        {
            put("_transaction_id", "text primary key");
            put("customer_id", "INTEGER");
            put("receiver_id", "INTEGER");
            put("transaction_time", "INTEGER");
            put("amount", "REAL");
        }
    };
    private List<String> columnNames;

    private static LinkedHashMap<Long, Transaction> transactions;

    private static void getUpdates(){
        try{

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Transaction> getCustomers() {
        getUpdates();
        return (new ArrayList<>(transactions.values()));
    }

    public static Transaction get(long transactionID){
        getUpdates();
        return null;
    }

    Transactions(Context context) {
        this(context, DB_NAME);
    }
    Transactions(Context context, String DB_NAME) {
        this(context, DB_NAME, DB_VER);
    }
    Transactions(Context context, String DB_NAME, int DB_VER) {
        this(context, DB_NAME, DB_VER, TABLE);
    }
    Transactions(Context context, String DB_NAME, int DB_VER, String TABLE) {
        this(context, DB_NAME, DB_VER, TABLE, columns);
    }
    Transactions(Context context, String DB_NAME, int DB_VER, String TABLE, LinkedHashMap<String, String> columns) {
        super(context, DB_NAME, DB_VER, TABLE, columns);
        columnNames = new ArrayList<>(columns.keySet());
    }


}
