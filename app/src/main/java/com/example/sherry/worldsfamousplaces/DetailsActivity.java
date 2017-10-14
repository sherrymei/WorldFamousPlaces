package com.example.sherry.worldsfamousplaces;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    SavedList savedList;
    landmarkTable table;
    String p;
    TextView place,city,country,continent,details;
    String PLACE,CITY,COUNTRY,CONTINENT,DETAILS;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SQLiteDatabase sqLiteDatabase;
        DatabaseHelper databaseHelper;
        try {
            sqLiteDatabase = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, MODE_PRIVATE, null);
            databaseHelper = new DatabaseHelper(this);
            table = new landmarkTable(this,sqLiteDatabase,databaseHelper);
            savedList = new SavedList(this,sqLiteDatabase,databaseHelper);

        }
        catch(SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

         place = (TextView) findViewById(R.id.place);
         city = (TextView) findViewById(R.id.city);
         country = (TextView) findViewById(R.id.country);
         continent = (TextView) findViewById(R.id.continent);
         details = (TextView) findViewById(R.id.details);

        p = getIntent().getExtras().getString("place");

        displayDetails();
    }

    private void displayDetails() {
         cursor = table.getDetails(p);
         PLACE = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_PLACE));
         CITY =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CITY));
         COUNTRY = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_COUNTRY));
         CONTINENT = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_CONTINENT));
         DETAILS = cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_DETAILS));

        place.setText(PLACE);
        city.setText(CITY + ", ");
        country.setText(COUNTRY + ", ");
        continent.setText(CONTINENT);
        details.setText(DETAILS);
    }
/*

    public void savePlace(View view) {
        double LAT = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_LAT));
        double LNG = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_LONG));
        savedList.addPlace(PLACE,LAT,LNG,CITY,COUNTRY,CONTINENT,DETAILS);
        Toast.makeText(getApplicationContext(), "Saved " + PLACE, Toast.LENGTH_SHORT).show();
    }
*/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
/*            case R.id.show_map:
                Intent mapIntent = new Intent (DetailsActivity.this,MapActivity.class);
                startActivity(mapIntent);
                return true;*/
            case R.id.add_to_list:
                double LAT = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_LAT));
                double LNG = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.KEY_LONG));
                savedList.addPlace(PLACE,LAT,LNG,CITY,COUNTRY,CONTINENT,DETAILS);
                Toast.makeText(getApplicationContext(), "Saved " + PLACE, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.explore:
                Intent exploreIntent = new Intent (DetailsActivity.this,ExploreActivity.class);
                startActivity(exploreIntent);
                return true;
            case R.id.savedList:
                Intent listIntent = new Intent (DetailsActivity.this, SavedListActivity.class);
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
}
