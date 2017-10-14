package com.example.sherry.worldsfamousplaces;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Sherry on 5/4/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "SHERRY";

    public static final String LIST = " SavedList ";
    public static final String TABLE = " Landmarks ";
    public static final String KEY_ID = " _id ";
    public static final String KEY_PLACE = "Place";
    public static final String KEY_LAT = "Latitude";
    public static final String KEY_LONG = "Longitude";
    public static final String KEY_CITY = "City";
    public static final String KEY_COUNTRY = "Country";
    public static final String KEY_CONTINENT = "Continent";
    public static final String KEY_DETAILS = "Details";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE + " ( " +
                    KEY_ID          + " INTEGER PRIMARY KEY autoincrement, " +
                    KEY_PLACE       + " TEXT, " +
                    KEY_LAT         + " FLOAT, " +
                    KEY_LONG        + " FLOAT, " +
                    KEY_CITY        + " TEXT, " +
                    KEY_COUNTRY     + " TEXT, " +
                    KEY_CONTINENT   + " TEXT, " +
                    KEY_DETAILS    + " TEXT) ";

    private static final String CREATE_LIST =
            "CREATE TABLE IF NOT EXISTS " + LIST + " ( " +
                    KEY_ID          + " INTEGER PRIMARY KEY autoincrement, " +
                    KEY_PLACE       + " TEXT, " +
                    KEY_LAT         + " FLOAT, " +
                    KEY_LONG        + " FLOAT, " +
                    KEY_CITY        + " TEXT, " +
                    KEY_COUNTRY     + " TEXT, " +
                    KEY_CONTINENT   + " TEXT, " +
                    KEY_DETAILS    + " TEXT) ";

    private static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE;

    private static final String DELETE_LIST =
            "DROP TABLE IF EXISTS " + LIST;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        db.execSQL(DELETE_LIST);
        onCreate(db);
    }
}

