package com.rudrashisdutta.thebank.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rudrashisdutta.thebank.banking.Transaction;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Transactions extends Database{

    private Context context;

    private static final String DB_NAME = "BANKING";
    private static final int DB_VER = 1;

    private static final String TABLE = "TRANSACTIONS";
    private static LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>(){
        {
            put("_transaction_id", "text primary key");
            put("customer_id", "INTEGER");
            put("receiver_id", "INTEGER");
            put("transaction_time", "INTEGER");
            put("amount", "REAL");
        }
    };
    private static List<String> columnNames = new ArrayList<>(columns.keySet());

    private static LinkedHashMap<String, Transaction> transactions;
    private static String ORDER;

    public Transactions(Context context) {
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
//        columnNames = new ArrayList<>(columns.keySet());
        this.context = context;
    }

    public boolean store(Transaction transaction){
        boolean success = false;
        try {
            database = getWritableDatabase();
            database.execSQL("insert into " + TABLE + " (" + formatColumnNamesAsString() + ") values(" + formatCustomerAsString(transaction) + ");");
            success = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
    private static @Nullable
    String formatColumnNamesAsString(){
        try {
            StringBuilder columnNamesInFormat = new StringBuilder();
            for(String columnName : columnNames){
                columnNamesInFormat.append(columnName).append(", ");
            }
            String value = columnNamesInFormat.toString();
            if(value.endsWith(", ")){
                value = value.substring(0, value.length() - 2);
            }
            return value;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static @NonNull
    String formatCustomerAsString(Transaction transaction){
        StringBuilder transactionAsString;
        transactionAsString = new StringBuilder();
        transactionAsString.append("'").append(transaction.getTransactionID()).append("',");
        transactionAsString.append(transaction.getCustomerID()).append(",");
        transactionAsString.append(transaction.getReceiverID()).append(",");
        transactionAsString.append(transaction.getTransactionTime()).append(",");
        transactionAsString.append(transaction.getAmount());
        return transactionAsString.toString();
    }
    private static void updateOrder(Context context){
        ORDER = new Application(context).getTransactionsOrder();
    }


    private static void getUpdates(Context context){
        try {
            updateOrder(context);
            Transactions transactionsDB = new Transactions(context);
            SQLiteDatabase database = transactionsDB.getReadableDatabase();
            try {
                transactions.clear();
            } catch (Exception ignore){}
            transactions = new LinkedHashMap<>();
            try(Cursor cursor = database.rawQuery("select * from " + TABLE + " ORDER BY " + columnNames.get(columnNames.size() - 2) + " " + ORDER + ";", null)){
                while(cursor.moveToNext()){
                    transactions.put(cursor.getString(0), Transaction.build(cursor.getString(0), cursor.getLong(1), cursor.getLong(2), cursor.getLong(3), cursor.getDouble(4)));
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Transaction> getTransactions(Context context) {
        getUpdates(context);
        return (new ArrayList<>(transactions.values()));
    }

    public static Transaction get(Context context, String transactionID){
        getUpdates(context);
        return transactions.get(transactionID);
    }

    public static void empty(Context context){
        try {
            SQLiteDatabase database = new Customers(context).getWritableDatabase();
            database.execSQL("delete from " + TABLE);
            getUpdates(context);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static long count(Context context){
        long count = 0;
        try {
            Transactions customers = new Transactions(context);
            customers.getWritableDatabase();
            SQLiteDatabase database = customers.getReadableDatabase();
            try (Cursor cursor = database.rawQuery("select count(*) from " + TABLE + ";", null)) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

}
