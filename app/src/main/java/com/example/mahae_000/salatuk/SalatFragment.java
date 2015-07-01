package com.example.mahae_000.salatuk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * A placeholder fragment containing a simple view.
 */



public class SalatFragment extends Fragment {
    public ArrayAdapter<String> PrayertAdapter;
    //private DbHelper mydb ;
    private String DB_NAME = "salat.sqlite";
    private String DB_PATH = "/data/data/com.example.mahae_000.salatuk/";
    private String DB_DIR_PATH = "/data/data/com.example.mahae_000.salatuk/databases";
    SQLiteDatabase  mDb;
    Cursor CR;
    DBHELPER dbhelper;
    public static final int NOTIFICATION_ID = 1;
    private void StoreDatabase() {
        try {
            //create the directory "databases"
            File databases_dir = new File(DB_DIR_PATH);
            databases_dir.mkdirs();
            //create the file "fooddb" in the directory "databases"
            File DbFile = new File(DB_PATH + DB_NAME);
            if (!DbFile.exists()) {
                DbFile.createNewFile();

                InputStream is = getActivity().getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(DbFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0)
                    fos.write(buffer,0,length );

                fos.flush();
                fos.close();
                is.close();
                DbFile.renameTo(getActivity().getDatabasePath(DB_NAME));

            }
        }
        catch (IOException e) {
            //Toast.makeText(MainActivity.this, "Error in Attatchment", Toast.LENGTH_SHORT).show();
            Log.v("toast","error in attachement");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StoreDatabase();
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.prayerfragment, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onStart() {
        super.onStart();
        FetchPrayerTask prayer = new FetchPrayerTask();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
// then you use
        String chosen_location;
        chosen_location=prefs.getString("location","egypt");

        //prayer.execute("egypt"); //string to do in background
        prayer.execute(chosen_location); //string to do in background
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchPrayerTask prayer = new FetchPrayerTask();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
// then you use
            String chosen_location;
            chosen_location = prefs.getString("location", "egypt");

            //prayer.execute("egypt"); //string to do in background
            prayer.execute(chosen_location); //string to do in background
            return true;
        }
        if (id == R.id.action_settings) {
            Log.v("settings is pressed", String.valueOf(id));
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_share) {

            //   ShareActionProvider mShareActionProvider =
            //         (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            // Attach an intent to this ShareActionProvider.  You can update this at any time,
            // like when the user selects a new piece of data they might like to share.
            //if (mShareActionProvider != null ) {
            Log.v("share is pressed", String.valueOf(id));
            String name = "  Powered by Salatuk";
            Intent shareintent = new Intent(Intent.ACTION_SEND);
            shareintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            //   shareintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            shareintent.setType("text/plain");
            String str=" ";
            for(int i = 0; i < PrayertAdapter.getCount(); i++){
                str = str+"\n"+(String)PrayertAdapter.getItem(i);

            }
            shareintent.putExtra(Intent.EXTRA_TEXT, str + name);
            shareintent.putExtra(Intent.EXTRA_SUBJECT, "Salatuk Times");


            startActivity(Intent.createChooser(shareintent, "Share Salatuk Times!"));
            return true;
            //mShareActionProvider.setShareIntent(shareintent);
        }

/*
        else {
                Log.d("inside else", "Share Action Provider is null?");
            }
*/


        return super.onOptionsItemSelected(item);
    }
    public SalatFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] list1 = {"Fajr - 3:07 am ", "shrouq  -5 am", "Duhr  -12:05 pm", "Asr  -3:30 pm", "Maghrib  -7:05 pm", "Isha  -8:35 pm"};
        //next line list or arraylist
        List<String> dailyprayer = new ArrayList<String>(Arrays.asList(list1));
        PrayertAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_prayer, R.id.list_item_prayer_textview, new ArrayList<String>());

        Log.v("LOG_TAG", "after array adapter");


        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        //  root view is the parent of the list view which is (fragment_main)
        final ListView prayerview = (ListView) rootView.findViewById(R.id.listview_prayer);  //name of listview in fragment_main

        prayerview.setAdapter(PrayertAdapter);
        prayerview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prayer = PrayertAdapter.getItem(position);
                //   Toast.makeText(getActivity(),forecast,Toast.LENGTH_SHORT).show(); dy el message el bttl3 kda lma yb2a 7aga successfull aw kda


                //  SQLiteDatabse mydatabase = openOrCreateDatabase("your database name",MODE_PRIVATE,null);
        /*
SQLiteDatabase SQ =mydb.getReadableDatabase();
        String[] columns={DbContract.Pray.COLUMN_PRAYER,DbContract.Pray.COLUMN_DESC,DbContract.Pray.COLUMN_BEFORE,DbContract.Pray.COLUMN_FARD,
        DbContract.Pray.COLUMN_AFTER,DbContract.Pray.COLUMN_PLURAL};

        Cursor CR =SQ.query(DbContract.Pray.TABLE_NAME,columns," id = ?",)
*/
        /*
        AssetDatabaseHelper dbHelper = new AssetDatabaseHelper(
                getActivity().getBaseContext(), "salat.sql");
        try {
            dbHelper.importIfNotExist();
        } catch (IOException e) {
            e.printStackTrace();
        }


        SQLiteDatabase sampleDB = dbHelper.getReadableDatabase();
        */
// new trial

                 dbhelper = new DBHELPER(getActivity().getBaseContext());


                mDb =dbhelper.getReadableDatabase();
                /*
                DatabaseHelper dbHelper2 = new DatabaseHelper(getActivity().getApplicationContext());
                try
                {
                    dbHelper2.createDataBase();
                }
                catch (IOException mIOException)
                {
                    Log.e("loginside catch", mIOException.toString() + "  UnableToCreateDatabase");
                    throw new Error("UnableToCreateDatabase");
                }
                try
                {
                    dbHelper2.openDataBase();
                    dbHelper2.close();
                    mDb = dbHelper2.getReadableDatabase();
                }
                catch (SQLException mSQLException)
                {
                    Log.e("logSQL exc", "open >>"+ mSQLException.toString());
                    throw mSQLException;
                }

*/

String sub_prayer=prayer.substring(0,3);
                Log.v("sub string is",sub_prayer);
                //str.substring(startIndex, endIndex);
                CR = mDb.rawQuery("SELECT desc,s_before,fard,s_after,plural FROM "
                        + DbContract.Pray.TABLE_NAME + " where " + DbContract.Pray.COLUMN_PRAYER + " like '%" + sub_prayer
                        + "%'", null);

                if( CR != null && CR.moveToFirst() ){

                    String desc_db=CR.getString(CR.getColumnIndex(DbContract.Pray.COLUMN_DESC));
                    Log.v("insert CR not null",desc_db);
                    String before_db=CR.getString(CR.getColumnIndex(DbContract.Pray.COLUMN_BEFORE));
                    String fard_db=CR.getString(CR.getColumnIndex(DbContract.Pray.COLUMN_FARD));
                    String after_db=CR.getString(CR.getColumnIndex(DbContract.Pray.COLUMN_AFTER));
                    String plural_db=CR.getString(CR.getColumnIndex(DbContract.Pray.COLUMN_PLURAL));
/*
                    if (!CR.isClosed())
                    {
                        CR.close();

                    }
                    */
                    //dbhelper.close();


                    Intent intent = new Intent(getActivity(),DetailActivity.class).putExtra(Intent.EXTRA_TEXT,prayer);
                    intent.putExtra("desc",desc_db);
                    intent.putExtra("before",before_db);
                    intent.putExtra("fard",fard_db);
                    intent.putExtra("after",after_db);
                    intent.putExtra("plural",plural_db);
                    startActivity(intent);
                }
             //   CR.moveToFirst();



             //   Log.v("CR",desc_db);


                //  DbContract.Pray pray1 = new DbContract.Pray();


            }

        });
        Log.v("LOG_TAG", "after setadapter");

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.


        return rootView;
    }
/*
    @Override
    public void onPause() {
        super.onPause();
        if (!CR.isClosed())
        {
            CR.close();

        }
        if (dbhelper!=null) {
            dbhelper.close();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        if (!CR.isClosed())
        {
            CR.close();

        }
        if (dbhelper!=null) {
            dbhelper.close();
        }
        }

       */


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!CR.isClosed())
        {
            CR.close();

        }
        if (dbhelper!=null) {
            dbhelper.close();
        }
        /*
        if ( mDb!= null)
        {
            mDb.close();
        }
        */
    }

    private String[] getPrayerTimeFromJson(String prayerJsonStr)
            throws JSONException {
        final String API_ITEM = "items";
        final String API_DATE = "date_for";
        final String API_FAJR = "fajr";
        final String API_SHUROOQ = "shurooq";
        final String API_DHUHR = "dhuhr";
        final String API_ASR = "asr";
        final String API_MAGHRIB = "maghrib";
        final String API_ISHA = "isha";

        JSONObject prayerJson = new JSONObject(prayerJsonStr);
        JSONArray dailyArray = prayerJson.getJSONArray(API_ITEM);
        final int numberOfItems = dailyArray.length();
        Log.v("number of items",String.valueOf(numberOfItems));
        JSONObject dateJson = dailyArray.getJSONObject(0);
        /*
      JSONObject FajrJson = dailyArray.getJSONObject(1);
        JSONObject ShrouqJson = dailyArray.getJSONObject(2);
        JSONObject DohrJson = dailyArray.getJSONObject(3);
        JSONObject AsrJson = dailyArray.getJSONObject(4);
        JSONObject MaghribJson = dailyArray.getJSONObject(5);
        JSONObject ishaJson = dailyArray.getJSONObject(6);
        */
        //JSONObject dateJson = prayerJson.getJSONObject(API_DATE);
        //  JSONObject FajrJson = prayerJson.getJSONObject(API_FAJR);
        //  JSONObject shrouqJson = prayerJson.getJSONObject(API_SHUROOQ);
        //JSONObject duhrJson = prayerJson.getJSONObject(API_DHUHR);
        //JSONObject asrJson = prayerJson.getJSONObject(API_ASR);
        //JSONObject maghribJson = prayerJson.getJSONObject(API_MAGHRIB);
//        JSONObject ishaJson = prayerJson.getJSONObject(API_ISHA);
        // JSONObject FajrJson;
        String[] resultStrs = new String[7];
        resultStrs[0]="Date  "+dateJson.getString(API_DATE);

        resultStrs[1]="Fajr     "+dateJson.getString(API_FAJR);
        resultStrs[2]="Shurooq  "+dateJson.getString(API_SHUROOQ);

        resultStrs[3]="Dhuhr    "+dateJson.getString(API_DHUHR);
        resultStrs[4]="Asr       "+dateJson.getString(API_ASR);
        resultStrs[5]="Maghrib   "+dateJson.getString(API_MAGHRIB);
        resultStrs[6]="Isha     "+dateJson.getString(API_ISHA);

        //  Log.v("dateJson.getstring", date);

        for (int i = 0; i < 7; i++) {
            //   resultStrs[i] = dateJson.getString(API_DATE);
            //resultStrs[i] = FajrJson.getString(API_FAJR);
            Log.v("FAJRJSON.getstring", resultStrs[i]);
        }
        //  resultStrs[0]=FajrJson.getString(API_FAJR);
        // Log.v("FAJRJSON.getstring", resultStrs[0]);
        return resultStrs;



    }


    public class FetchPrayerTask extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchPrayerTask.class.getSimpleName();

        @Override
        //     protected String[] doInBackground(String... params) {
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String prayerJsonStr = null;
            String format = "json";
            String duration = "daily";
            String daylight = "false";
            String method = "1";
            Log.v(LOG_TAG, "before try");

            try {

                final String PRAYER_BASE_URL =
                        "http://muslimsalat.com";

                Log.v(LOG_TAG, "before URI");
                Uri builtUri = Uri.parse(PRAYER_BASE_URL).buildUpon()
                        .appendPath(params[0]).appendPath(duration).appendPath(daylight).appendPath(method).appendPath(".json").build();


                //  .appendQueryParameter(UNITS_PARAM, units)
                Log.v(LOG_TAG, "before url");
                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "after url");
                /*
                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM,params[0])
                        .appendQueryParameter(FORMAT_PARAM,format)
                        .appendQueryParameter(UNITS_PARAM,units)
                        .appendQueryParameter(DAYS_PARAM,Integer.toString(numDays)).build();
                        .appendQueryParameter(DAYS_PARAM,Integer.toString(numDays)).build();
                Log.v(LOG_TAG,"after building URI");
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
              //  URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");
                URL url = new URL(builtUri.toString());
                */
                Log.v(LOG_TAG, "BUILT URI" + builtUri.toString());


                //    URL url = new URL("http://muslimsalat.com/daily.json?key=a850ab944225c55473bbfcf28220be4a");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Log.v("reponse code", String.valueOf(urlConnection.getResponseCode()));


                //if (urlConnection.getResponseCode() != 200) {
                //  Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();// show this message when no internet connection
                //}
//else
                //              {


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                prayerJsonStr = buffer.toString();
                Log.v("string from api", prayerJsonStr);


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            try {
                return getPrayerTimeFromJson(prayerJsonStr);

            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }




        @Override
        protected void onPostExecute(String[] result) {    //result da el rage3 mn do in background
            if (result != null) {

                PrayertAdapter.clear();
                for (String dayprayer : result)
                {
                    PrayertAdapter.add(dayprayer);   // 3shan n7ot el data el rag3a mn el server fy el array adapter
                }

            }

        }

    }
}

//}

