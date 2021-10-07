package com.rudrashisdutta.thebank.database;

import android.content.Context;

import java.util.LinkedHashMap;

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
            put("balance", "");
            put("", "");
        }
    };


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
    }
}
