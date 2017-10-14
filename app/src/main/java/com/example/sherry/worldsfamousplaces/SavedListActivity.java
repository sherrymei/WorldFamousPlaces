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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class SavedListActivity extends AppCompatActivity {
    
    private SavedList savedlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_list);
        
        SQLiteDatabase sqLiteDatabase;
        DatabaseHelper databaseHelper;

        try {
            sqLiteDatabase = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, MODE_PRIVATE, null);
            databaseHelper = new DatabaseHelper(this);
            savedlist = new SavedList(this,sqLiteDatabase,databaseHelper);
        }
        catch(SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        displayListView();
    }

    private void displayListView() {
        Cursor cursor = savedlist.getAll();
        String[] columns = new String[] {
                DatabaseHelper.KEY_PLACE,
                DatabaseHelper.KEY_CITY,
                DatabaseHelper.KEY_COUNTRY
        };

        int[] to = new int[]{
                R.id.placeName,
                R.id.cityName,
                R.id.countryName
        };

        ListView listView = (ListView) findViewById(R.id.listView2);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.list_row,cursor,columns,to,0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Cursor curs = (Cursor) listView.getItemAtPosition(position);
                String place = curs.getString(curs.getColumnIndexOrThrow(DatabaseHelper.KEY_PLACE));
                Intent detailIntent = new Intent(SavedListActivity.this,DetailsActivity.class);
                detailIntent.putExtra("place",place);
                startActivity(detailIntent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String place = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PLACE));
                savedlist.deletePlace(place);
                Toast.makeText(getApplicationContext(), "deleted " + place, Toast.LENGTH_SHORT).show();
                displayListView();
                return true;
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.explore, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.explore:
                Intent exploreIntent = new Intent (SavedListActivity.this,ExploreActivity.class);
                startActivity(exploreIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onPause() {
        super.onPause();
        savedlist.close();
    }
}
