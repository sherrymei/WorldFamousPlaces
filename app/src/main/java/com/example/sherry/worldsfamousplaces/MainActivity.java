package com.example.sherry.worldsfamousplaces;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private landmarkTable table;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase sqLiteDatabase;


        try {
            sqLiteDatabase = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, MODE_PRIVATE, null);
            databaseHelper = new DatabaseHelper(this);
            table = new landmarkTable(this,sqLiteDatabase,databaseHelper);

        }
        catch(SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        table.deleteAllLandmarks();

        new DownloadJSON().execute();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.explore:
                Intent exploreIntent = new Intent (MainActivity.this,ExploreActivity.class);
                startActivity(exploreIntent);
                return true;
            case R.id.savedList:
                Intent listIntent = new Intent (MainActivity.this, SavedListActivity.class);
                startActivity(listIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        table.close();
    }


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        protected void onPreExecute(){
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            String city, place, continent,country,details;
            double latitude, longitude;

            jsonObject = JSONfunctions.getJSONfromURL("http://cs.bc.edu/~signoril/world_famous_landmarks.json");

            try {
                jsonArray = jsonObject.getJSONArray("landmark");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);

                     city = jsonObject.optString("city");
                     country = jsonObject.optString("country");
                     latitude = jsonObject.optDouble("latitude");
                     longitude = jsonObject.optDouble("longitude");
                     place = jsonObject.optString("place");
                     continent = jsonObject.optString("continent");
                     details = jsonObject.optString("details");
                     table.addLandmark(place,latitude,longitude,city,country,continent,details);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(result);
        }
    }

}