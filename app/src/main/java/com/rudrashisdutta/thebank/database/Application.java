package com.rudrashisdutta.thebank.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rudrashisdutta.thebank.banking.Customer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Application extends Database{

    private static final String DB_NAME = "APPLICATION";
    private static final int DB_VER = 1;
    private static final String TABLE = "BANK";
    private static final LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>(){
        {
            put("_key", "text primary key"); // APP - only possible value
            put("_initialized", "INTEGER"); // 1 - 10 values are present in customer
            put("order_customers", "text"); // ASC or DESC
            put("order_transactions", "text"); // ASC or DESC
        }
    };
    private static final List<String> columnNames = new ArrayList<>(columns.keySet());
    private static final String _key = "app";
    private static final int INITIALIZED = 1;
    public static final String ASCENDING = "ASC";
    public static final String DESCENDING = "DESC";

    private SQLiteDatabase database;

    public Application(Context context) {
        this(context, DB_NAME, DB_VER, TABLE, columns);
    }
    Application(Context context, String DB_NAME, int DB_VER, String TABLE, LinkedHashMap<String, String> columns) {
        super(context, DB_NAME, DB_VER, TABLE, columns);
    }

    public void updateCustomersOrder(boolean ascending){
        if(ascending){
            updateApp(columnNames.get(2), ASCENDING);
        } else {
            updateApp(columnNames.get(2), DESCENDING);
        }
    }
    public String getCustomerOrder(){
        return getAppData(columnNames.get(2));
    }

    public void updateTransactionsOrder(boolean ascending){
        if(ascending){
            updateApp(columnNames.get(3), ASCENDING);
        } else {
            updateApp(columnNames.get(3), DESCENDING);
        }
    }
    public String getTransactionsOrder(){
        return getAppData(columnNames.get(3));
    }

    private void updateApp(String columnName, String value){
        try {
            database = getWritableDatabase();
            database.execSQL("update " + TABLE + " set " + columnName + " = '" + value + "' where " + columnNames.get(0) + " = '" + _key + "';");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getAppData(String columnName){

        database = getReadableDatabase();
        try(Cursor cursor = database.rawQuery("select " + columnName + " from " + TABLE + " where " + columnNames.get(0) + " = '" + _key + "';", null)){
            cursor.moveToFirst();
            return cursor.getString(0);
        }
    }


    public static boolean createDataIfNotFound(Context context){
        boolean found = false;
        try{
            long count = 0;
             try{
                 count = count(context);
             } catch (Exception e){
                 e.printStackTrace();
             }
            if(count == 0){
                initialize(context);
                found = true;
            }
            count = 0;
            try{
                count = Customers.count(context);
            } catch (Exception e){
                e.printStackTrace();
            }
            if(count != 10){
                initializeCustomers(context);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return found;
    }
    private static long count(Context context){
        long count = 0;
        try {
            Application application = new Application(context);
            application.getWritableDatabase();
            SQLiteDatabase database = application.getReadableDatabase();
            Cursor cursor = database.rawQuery("select count(*) from " + TABLE + ";", null);
            cursor.moveToFirst();
            count = cursor.getInt(0);
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }
    public static void initialize(Context context){
        try {
            SQLiteDatabase database = new Application(context).getWritableDatabase();
            database.execSQL("insert into " + TABLE + " (" + formatColumnNamesAsString() + ") values(" + formatCustomerAsString(ASCENDING, ASCENDING) + ");");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void initializeCustomers(Context context){
        try {
            Customers customers = new Customers(context);
            for(Customer customer : Customer.getDefaultCustomers()){
                customers.store(customer);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
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
    String formatCustomerAsString(String orderOfCustomers, String orderOfTransactions){
        StringBuilder customerAsString;
        customerAsString = new StringBuilder();
        customerAsString.append("'").append(Application._key).append("',");
        customerAsString.append(Application.INITIALIZED).append(",'");
        customerAsString.append(orderOfCustomers).append("','");
        customerAsString.append(orderOfTransactions).append("'");
        return customerAsString.toString();
    }

}
