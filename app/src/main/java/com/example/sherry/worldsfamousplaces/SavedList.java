package com.example.sherry.worldsfamousplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sherry on 5/9/2017.
 */

public class SavedList {


    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public SavedList(Context context, SQLiteDatabase database, DatabaseHelper databaseHelper) {
        this.context = context;
        this.database = database;
        this.databaseHelper = databaseHelper;
        this.database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        if (databaseHelper != null) databaseHelper.close();
    }

    public boolean addPlace(String place, double latitude, double longitude, String city, String country, String continent, String details) {
        this.database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PLACE, place);
        values.put(DatabaseHelper.KEY_LAT, latitude);
        values.put(DatabaseHelper.KEY_LONG, longitude);
        values.put(DatabaseHelper.KEY_CITY, city );
        values.put(DatabaseHelper.KEY_COUNTRY, country);
        values.put(DatabaseHelper.KEY_CONTINENT,continent);
        values.put(DatabaseHelper.KEY_DETAILS,details);
        long a =  database.insert(DatabaseHelper.LIST, null, values);
        return a > 0;
    }

    public Cursor getAll() {
        this.database = databaseHelper.getWritableDatabase();
        String[] values = new String[] {
                DatabaseHelper.KEY_ID, DatabaseHelper.KEY_PLACE,
                DatabaseHelper.KEY_LAT, DatabaseHelper.KEY_LONG,
                DatabaseHelper.KEY_CITY,DatabaseHelper.KEY_COUNTRY,
                DatabaseHelper.KEY_CONTINENT,DatabaseHelper.KEY_DETAILS
        };

        Cursor cursor = database.query(DatabaseHelper.LIST, values, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deletePlace(String place) {
        database.delete(DatabaseHelper.LIST,DatabaseHelper.KEY_PLACE  + " = ?", new String[]{place});
    }
}
