package com.example.myapplication.util;

import ohos.app.Context;
import ohos.app.ContextInvalidArgException;
import ohos.data.DatabaseHelper;
import ohos.data.preferences.Preferences;

public class LocalDataUtil{
    public static DatabaseHelper databaseHelper;
    public static Preferences preferences;
    public static void initDb(Context context,String dbname){
        databaseHelper=new DatabaseHelper(context);
        preferences=databaseHelper.getPreferences(dbname);
    }

}
