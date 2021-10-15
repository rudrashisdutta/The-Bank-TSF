package com.rudrashisdutta.thebank.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Database extends SQLiteOpenHelper {

    private Context context;
    protected String DB_NAME;
    protected int DB_VER;

    private List<String> TABLES;

    private Map<String, String> columns;

    protected SQLiteDatabase database;

    Database(Context context, String DB_NAME, int DB_VER, List<String> TABLES, Map<String, String> columns){
        super(context, DB_NAME, null, DB_VER);
        Log.e("EEE","sfdgshouguyds "+TABLES.toString());
        this.context = context;
        this. DB_NAME = DB_NAME;
        this.DB_VER = DB_VER;
        this.TABLES = TABLES;
        this.columns = columns;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            for(String TABLE : TABLES){
                Log.e("t", "CREATING!");
                sqLiteDatabase.execSQL("create table " + TABLE + " (" + columns.get(TABLE) + ");");
                Log.e("t", "create table " + TABLE + " (" + columns.get(TABLE) + ");");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            for(String TABLE : TABLES){
                sqLiteDatabase.execSQL("drop table if exists " + TABLE);
            }
            onCreate(sqLiteDatabase);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    protected static @Nullable String formatColumnNamesWithDataTypes(LinkedHashMap<String, String> columns){
        try{
            StringBuilder columnNamesWithDataTypes = new StringBuilder();
            for(Map.Entry<String, String> column: columns.entrySet()){
                columnNamesWithDataTypes.append(column.getKey()).append(" ").append(column.getValue()).append(",");
            }
            String value = columnNamesWithDataTypes.toString();
            if(value.endsWith(",")){
                value = value.substring(0, value.length() - 1);
            }
            return value;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
