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

public class Customers extends Database{

    private Context context;

    private static final String DB_NAME = "BANKING";
    private static final int DB_VER = 1;

    private static final String TABLE = "CUSTOMERS";
    private static final LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>(){
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
    private static final List<String> columnNames = new ArrayList<>(columns.keySet());

    private static LinkedHashMap<Long, Customer> customers;
    private static String ORDER;


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
        customerAsString.append(customer.getCustomerName()).append("',");
        customerAsString.append(customer.getDOB()).append(",'");
        customerAsString.append(customer.getEmail()).append("',");
        customerAsString.append(customer.getMobileNumber()).append(",'");
        customerAsString.append(customer.getPAN()).append("','");
        customerAsString.append(customer.getAadhaar()).append("',");
        customerAsString.append(customer.getBalance()).append(",'");
        customerAsString.append(customer.getAddress()).append("'");
        return customerAsString.toString();
    }

    private static void getUpdates(Context context){
        SQLiteDatabase database = new Customers(context).getReadableDatabase();
        customers.clear();
        customers = new LinkedHashMap<>();
        try(Cursor cursor = database.rawQuery("select * from " + TABLE + " ORDER BY " + ORDER + "", null)){
            while(cursor.moveToNext()){
                customers.put(cursor.getLong(0), Customer.build(cursor.getLong(0), cursor.getString(1), cursor.getLong(2), cursor.getString(3), cursor.getLong(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7), cursor.getString(8)));
            }
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
}
