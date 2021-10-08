package com.rudrashisdutta.thebank.database;

import android.content.Context;

import com.rudrashisdutta.thebank.banking.Customer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Customers extends Database{

    private static String DB_NAME = "BANKING";
    private static int DB_VER = 1;

    private static String TABLE = "CUSTOMERS";
    private static LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>(){
        {
            put("_customer_id", "INTEGER primary key");
            put("customer_name", "text");
            put("DOB", "INTEGER");
            put("email", "text");
            put("mobile", "INTEGER");
            put("PAN", "text");
            put("aadhaar", "text");
            put("balance", "REAL");
            put("address", "text");
        }
    };
    private List<String> columnNames;

    private static LinkedHashMap<Long, Customer> customers;

    private static void getUpdates(){
        try{

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Customer> getCustomers() {
        getUpdates();
        return (new ArrayList<>(customers.values()));
    }

    public static Customer get(long customerID){
        getUpdates();
        return null;
    }


    Customers(Context context) {
        this(context, DB_NAME);
    }
    Customers(Context context, String DB_NAME) {
        this(context, DB_NAME, DB_VER);
    }
    Customers(Context context, String DB_NAME, int DB_VER) {
        this(context, DB_NAME, DB_VER, TABLE);
    }
    Customers(Context context, String DB_NAME, int DB_VER, String TABLE) {
        this(context, DB_NAME, DB_VER, TABLE, columns);
    }
    Customers(Context context, String DB_NAME, int DB_VER, String TABLE, LinkedHashMap<String, String> columns) {
        super(context, DB_NAME, DB_VER, TABLE, columns);
        columnNames = new ArrayList<>(columns.keySet());

    }
}
