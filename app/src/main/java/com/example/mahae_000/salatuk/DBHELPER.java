package com.example.mahae_000.salatuk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Omar on 6/19/2015.
 */
public class DBHELPER extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "salat.sqlite";

    private static int DATABASE_VERSION = 1;


    public DBHELPER(Context C) {
        super(C, DATABASE_NAME, null, DATABASE_VERSION);
        C.openOrCreateDatabase(DATABASE_NAME,C.MODE_PRIVATE,null);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { }


}



