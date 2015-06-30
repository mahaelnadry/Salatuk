package com.example.mahae_000.salatuk;

/**
 * Created by mahae_000 on 6/30/2015.
 */
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class DbContract {

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /*
        Inner class that defines the table contents of the location table
        Students: This is where you will add the strings.  (Similar to what has been
        done for WeatherEntry)
     */


    /* Inner class that defines the table contents of the weather table */
    public static final class Pray implements BaseColumns {

        public static final String TABLE_NAME = "pray";

        // Column with the foreign key into the location table.
        public static final String COLUMN_PRAYER = "prayer";
        public static final String COLUMN_DESC = "desc";
        // Date, stored as long in milliseconds since the epoch
        public static final String COLUMN_BEFORE = "s_before";
        // Weather id as returned by API, to identify the icon to be used
        public static final String COLUMN_FARD = "fard";

        // Short description and long description of the weather, as provided by API.
        // e.g "clear" vs "sky is clear".
        public static final String COLUMN_AFTER = "s_after";

        // Min and max temperatures for the day (stored as floats)
        public static final String COLUMN_PLURAL = "plural";

    }
}