package com.example.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Basic database class for application
 * {@Link AppProvider}is the only class that uses this
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    public static final String DATABASE_NAME = "TaskTimer.db";
    public static final int DATABASE_VRESION = 1;

    private static AppDatabase instance = null;

    // simpleton ensures on one instance of this class is ever created (which the constructor is private)
    private AppDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VRESION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    static AppDatabase getInstance(Context context) {
     if (instance == null) {
         Log.d(TAG, "getInstance: creating new instance");
         instance = new AppDatabase(context);
     }

     return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");
        String sSQL;

        sSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + " ("
                + TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + TasksContract.Columns.TASKS_NAME + " TEXT NOT NULL, "
                + TasksContract.Columns.TASKS_DESCRIPTION + " TEXT, "
                + TasksContract.Columns.TASKS_SORTORDER +" INTEGER, "
                + TasksContract.Columns.TASKS_CATEGORY_ID + " INTEGER);";

        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");

        switch (oldVersion) {
            case 1:
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown new version: " + newVersion);
        }

        Log.d(TAG, "onUpgrade: ends");
    }
}
