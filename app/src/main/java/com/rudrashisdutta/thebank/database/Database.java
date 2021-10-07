package com.rudrashisdutta.thebank.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedHashMap;

public class Database extends SQLiteOpenHelper {

    private Context context;
    private String DB_NAME;
    private int DB_VER;

    private String TABLE;

    private LinkedHashMap<String, String> columns;

    Database(Context context, String DB_NAME, int DB_VER, String TABLE, LinkedHashMap<String, String> columns){
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
        this. DB_NAME = DB_NAME;
        this.DB_VER = DB_VER;
        this.columns = columns;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void formatColumnNamesWithDataTypes(){

    }
}
