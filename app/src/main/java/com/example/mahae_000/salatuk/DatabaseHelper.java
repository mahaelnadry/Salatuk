package com.example.mahae_000.salatuk;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by mahae_000 on 6/30/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    //public static String DB_PATH = "/data/data/com.example.mahae_000.salatuk/databases/";
    public static String DB_PATH = "/app/src/main/res/raw/";
    //public static String DB_PATH = "";
    private static String DB_NAME = "salat.sql";
    private static String DB_NAME_MY = "salat.sql";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the  application assets and resources.
     * @param context
     */

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
        // for first database;
        boolean dbExist = checkDataBase(DB_NAME);
        if(!dbExist){
            try {
                copyDataBase(DB_NAME_MY,DB_NAME);
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(String DB){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,  SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){}

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase(String assetfile,String DB) {

        //Open your local db as the input stream
        InputStream myInput = null;
        //Open the empty db as the output stream
        OutputStream myOutput = null;
        try {
            myInput = myContext.getAssets().open(assetfile);

            // Path to the just created empty db
            String outFileName = DB_PATH + DB;

            myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }


            System.out.println("***************************************");
            System.out.println("####### Data base copied ##############");
            System.out.println("***************************************");


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            //Close the streams
            try {
                myOutput.flush();
                myOutput.close();
                myInput.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void openDataBase() {

        try {
            //Open the database
            String myPath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}