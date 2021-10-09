package com.rudrashisdutta.thebank.database;

import android.content.Context;

import java.util.LinkedHashMap;

public class Application extends Database{

    private static String DB_NAME = "APPLICATION";
    private static int DB_VER = 1;
    private static String TABLE = "BANK";
    private static LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>(){
        {
            put("", "");
        }
    };;

    Application(Context context) {
        this(context, DB_NAME, DB_VER, TABLE, columns);
    }
    Application(Context context, String DB_NAME, int DB_VER, String TABLE, LinkedHashMap<String, String> columns) {
        super(context, DB_NAME, DB_VER, TABLE, columns);
    }
}
