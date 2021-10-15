package com.rudrashisdutta.thebank.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Banking extends Database{

    protected static final String CUSTOMER_TABLE = "CUSTOMERS";
    protected static final String TRANSACTION_TABLE = "TRANSACTIONS";
    private static final List<String> TABLES = new ArrayList<String>() {
            {
                add(CUSTOMER_TABLE);
                add(TRANSACTION_TABLE);
            }
    };
    protected static LinkedHashMap<String, String> transactionTableColumns = new LinkedHashMap<String, String>(){
        {
            put("_transaction_id", "text primary key");
            put("customer_id", "INTEGER");
            put("receiver_id", "INTEGER");
            put("transaction_time", "INTEGER");
            put("amount", "REAL");
        }
    };
    protected static LinkedHashMap<String, String> customerTableColumns = new LinkedHashMap<String, String>(){
        {
            put("_customer_id", "INTEGER primary key");
            put("customer_name", "text");
            put("DOB", "text");
            put("email", "text");
            put("mobile", "text");
            put("PAN", "text");
            put("aadhaar", "text");
            put("balance", "REAL");
            put("address", "text");
        }
    };
    private static String getTransactionTableColumns() {
        return formatColumnNamesWithDataTypes(transactionTableColumns);
    }
    private static String getCustomerTableColumns() {
        return formatColumnNamesWithDataTypes(customerTableColumns);
    }
    private static final Map<String, String> columns = new HashMap<String, String>(){
        {
            put(CUSTOMER_TABLE, getCustomerTableColumns());
            put(TRANSACTION_TABLE, getTransactionTableColumns());
        }
    };

    Banking(Context context, String DB_NAME, int DB_VER) {
        super(context, DB_NAME, DB_VER, TABLES, columns);
    }
}
