package com.example.sherry.worldsfamousplaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherry on 5/6/2017.
 */

public class landmarkTable {

    private final Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    private List<Double> lat_doubles, lng_doubles;
    private List<String> places;


    public landmarkTable(Context context, SQLiteDatabase database, DatabaseHelper databaseHelper) {
        this.context = context;
        this.database = database;
        this.databaseHelper = databaseHelper;
        this.database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        if (databaseHelper != null) databaseHelper.close();
    }

    public boolean addLandmark(String place, double latitude, double longitude, String city, String country, String continent, String details) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PLACE, place);
        values.put(DatabaseHelper.KEY_LAT, latitude);
        values.put(DatabaseHelper.KEY_LONG, longitude);
        values.put(DatabaseHelper.KEY_CITY, city);
        values.put(DatabaseHelper.KEY_COUNTRY, country);
        values.put(DatabaseHelper.KEY_CONTINENT, continent);
        values.put(DatabaseHelper.KEY_DETAILS, details);
        long a = database.insert(DatabaseHelper.TABLE, null, values);
        return a > 0;
    }

    public List<Double> getLat(String continent) {

        this.database = databaseHelper.getWritableDatabase();
        lat_doubles = new ArrayList<>();
        String[] values = new String[]{DatabaseHelper.KEY_LAT};
        Cursor cursor = database.query(DatabaseHelper.TABLE, values, DatabaseHelper.KEY_CONTINENT + " = ? ", new String[]{continent}, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                double lat = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_LAT));
                lat_doubles.add(lat);
                cursor.moveToNext();
            }
        }
        return lat_doubles;
    }

    public List<Double> getLng(String continent) {
        this.database = databaseHelper.getWritableDatabase();
        lng_doubles = new ArrayList<>();
        String[] values = new String[]{DatabaseHelper.KEY_LONG};
        Cursor cursor = database.query(DatabaseHelper.TABLE, values, DatabaseHelper.KEY_CONTINENT + "= ?", new String[]{continent}, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                lng_doubles.add(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_LONG)));
                cursor.moveToNext();
            }
        }
        return lng_doubles;
    }


    public List<String> getPlace(String continent) {
        this.database = databaseHelper.getWritableDatabase();
        String[] values = new String[]{DatabaseHelper.KEY_PLACE};
        Cursor cursor = database.query(DatabaseHelper.TABLE, values, DatabaseHelper.KEY_CONTINENT + "= ?", new String[]{continent}, null, null, null);
        places = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                places.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_PLACE)));
                cursor.moveToNext();
            }
        }
        return places;
    }

    public Cursor getDetails(String place) {
        this.database = databaseHelper.getWritableDatabase();
        String[] values = new String[]{
                DatabaseHelper.KEY_PLACE,
                DatabaseHelper.KEY_LAT,
                DatabaseHelper.KEY_LONG,
                DatabaseHelper.KEY_CITY,
                DatabaseHelper.KEY_COUNTRY,
                DatabaseHelper.KEY_CONTINENT,
                DatabaseHelper.KEY_DETAILS};
        Cursor cursor = database.query(DatabaseHelper.TABLE, values, DatabaseHelper.KEY_PLACE + "= ?", new String[]{place}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void deleteAllLandmarks() {
        database.delete(DatabaseHelper.TABLE, null, null);
    }

}
/*    public Cursor getAllLandmarks() {

        String[] values = new String[] {
                DatabaseHelper.KEY_ID, DatabaseHelper.KEY_PLACE,
                DatabaseHelper.KEY_LAT, DatabaseHelper.KEY_LONG,
                DatabaseHelper.KEY_CITY,DatabaseHelper.KEY_COUNTRY,
                DatabaseHelper.KEY_CONTINENT
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE, values, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getCountry() {
        Cursor cursor;
        String[] values = new String[] {DatabaseHelper.KEY_COUNTRY};
        cursor = database.query(DatabaseHelper.TABLE, values, null , null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }*/

/*    public void deleteLandmark(String place) {
        database.delete(DatabaseHelper.TABLE,DatabaseHelper.KEY_PLACE  + " = ?", new String[]{place});
    }*/



/*    public boolean updateLandmark(int id, String place, double latitude, double longitude, String city, String country) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.KEY_PLACE, place);
        values.put(DatabaseHelper.KEY_LAT, latitude);
        values.put(DatabaseHelper.KEY_LONG, longitude);
        values.put(DatabaseHelper.KEY_CITY, city );
        values.put(DatabaseHelper.KEY_COUNTRY, country);
        String[] args = new String[]{String.valueOf(id)};
        int upd = database.update(DatabaseHelper.TABLE, values, DatabaseHelper.KEY_ID + " = ? " , args);
        return upd > 0 ;
    }*/
