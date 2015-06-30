package com.example.mahae_000.salatuk;

/**
 * Created by mahae_000 on 6/30/2015.
 */


import com.example.mahae_000.salatuk.DbContract.Pray;


/**
 * Manages a local database for weather data.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "salat.db";
    public static final String CONTACTS_TABLE_NAME = "pray";

    private HashMap hp;

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //db.execSQL(
          //      "create table contacts " +
            //            "(id integer primary key, name text,phone text,email text, street text,place text)"
        //);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
      //  db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }



    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }





    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from pray", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(Pray.COLUMN_PRAYER)));
            res.moveToNext();
        }
        return array_list;
    }
}