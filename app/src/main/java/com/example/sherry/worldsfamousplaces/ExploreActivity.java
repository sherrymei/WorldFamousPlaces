package com.example.sherry.worldsfamousplaces;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExploreActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        final String[] continents = new String[]{"North America", "South America", "Europe", "Africa", "Asia", "Australia"};
        ListView listView = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, continents);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                String continent = continents[position];
                Intent intent = new Intent(ExploreActivity.this, MapActivity.class);
                intent.putExtra("continent", continent);
                startActivity(intent);
            }
        });
    }
}


//    private void displayListView() {
//        Cursor cursor = table.getCountry();
//        String[] columns = new String[]{DatabaseHelper.KEY_COUNTRY};
//        int[] to = new int[]{R.id.country};
//        listView = (ListView) findViewById(R.id.listView1);
//        adapter = new SimpleCursorAdapter(this, R.layout.list_row, cursor, columns, to, 0);
//        listView.setAdapter(adapter);
//
//        listViewListener();
//    }
//

