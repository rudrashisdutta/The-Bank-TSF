package com.rudrashisdutta.thebank.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rudrashisdutta.thebank.banking.Customer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Customers extends Banking{

    private Context context;

    private static final String DB_NAME = "BANKING";
    private static final int DB_VER = 1;

    private static final String TABLE = CUSTOMER_TABLE;
    private static final LinkedHashMap<String, String> columns = customerTableColumns;


    private static final List<String> columnNames = new ArrayList<>(columns.keySet());

    private static LinkedHashMap<Long, Customer> customers;
    private static String ORDER;


    public Customers(Context context) {
        this(context, DB_NAME, DB_VER);
    }
    Customers(Context context, String DB_NAME, int DB_VER) {
        super(context, DB_NAME, DB_VER);
//        columnNames = new ArrayList<>(columns.keySet());
        this.context = context;
    }

    public boolean store(Customer customer){
        boolean success = false;
        try {
            database = getWritableDatabase();
            database.execSQL("insert into " + TABLE + " (" + formatColumnNamesAsString() + ") values(" + formatCustomerAsString(customer) + ");");
            success = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
    public Customer updateBalance(Customer customer, double newBalance){
        try{
            database = getWritableDatabase();
            database.execSQL("update " + TABLE + " set " + columnNames.get(columnNames.size() - 2) + " = " + newBalance + " where " + columnNames.get(0) + " = " + customer.getCustomerID() + ";");
            Customer.build(customer, newBalance);
        } catch (Exception e){
            e.printStackTrace();
        }
        return customer;
    }

    private static @Nullable String formatColumnNamesAsString(){
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
    private static @NonNull String formatCustomerAsString(Customer customer){
        StringBuilder customerAsString;
        customerAsString = new StringBuilder();
        customerAsString.append(customer.getCustomerID()).append(",'");
        customerAsString.append(customer.getCustomerName()).append("','");
        customerAsString.append(customer.getDOB()).append("','");
        customerAsString.append(customer.getEmail()).append("','");
        customerAsString.append(customer.getMobileNumber()).append("','");
        customerAsString.append(customer.getPAN()).append("','");
        customerAsString.append(customer.getAadhaar()).append("',");
        customerAsString.append(customer.getBalance()).append(",'");
        customerAsString.append(customer.getAddress()).append("'");
        return customerAsString.toString();
    }
    private static void updateOrder(Context context){
        ORDER = new Application(context).getCustomerOrder();
    }


    private static void getUpdates(Context context){
        try {
            updateOrder(context);
            Customers customer = new Customers(context);
            SQLiteDatabase database = customer.getReadableDatabase();
            try {
                customers.clear();
            } catch (Exception ignore){}
            customers = new LinkedHashMap<>();
            try(Cursor cursor = database.rawQuery("select * from " + TABLE + " ORDER BY " + columnNames.get(1) + " " + ORDER + ";", null)){
                while(cursor.moveToNext()){
                    customers.put(cursor.getLong(0), Customer.build(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7), cursor.getString(8)));
                }
            }
            customer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Customer> getCustomers(Context context) {
        getUpdates(context);
        return (new ArrayList<>(customers.values()));
    }

    public static Customer get(Context context, long customerID){
        getUpdates(context);
        return customers.get(customerID);
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
            Customers customers = new Customers(context);
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
