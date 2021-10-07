package com.rudrashisdutta.thebank.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class Database extends SQLiteOpenHelper {

    private Context context;
    protected String DB_NAME;
    protected int DB_VER;

    private String TABLE;

    private LinkedHashMap<String, String> columns;

    Database(Context context, String DB_NAME, int DB_VER, String TABLE, LinkedHashMap<String, String> columns){
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
        this. DB_NAME = DB_NAME;
        this.DB_VER = DB_VER;
        this.TABLE = TABLE;
        this.columns = columns;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            sqLiteDatabase.execSQL("create table " + TABLE + " (" + formatColumnNamesWithDataTypes() + ");");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            sqLiteDatabase.execSQL("drop table if exists " + TABLE);
            onCreate(sqLiteDatabase);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private @Nullable String formatColumnNamesWithDataTypes(){
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
