package com.example.sherry.worldsfamousplaces;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {

    private landmarkTable table;
    private GoogleMap googleMap;
    private String cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SQLiteDatabase sqLiteDatabase;
        DatabaseHelper databaseHelper;

        try {
            sqLiteDatabase = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, MODE_PRIVATE, null);
            databaseHelper = new DatabaseHelper(this);
            table = new landmarkTable(this,sqLiteDatabase,databaseHelper);
        }
        catch(SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        cont =  getIntent().getExtras().getString("continent");
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        table.close();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapLoadedCallback(this);
        UiSettings mapSettings = googleMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setTiltGesturesEnabled(false);
        mapSettings.setZoomGesturesEnabled(false);
        mapSettings.setRotateGesturesEnabled(false);
        mapSettings.setCompassEnabled (false);
        mapSettings.setMapToolbarEnabled(false);
        mapSettings.setMyLocationButtonEnabled(false);


        switch (cont){
            case "North America":
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.827930, -99.025471),3));
                break;
            case "South America":
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-21.717201, -60.783895),3));
                break;
            case "Europe":
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(50.911728, 18.694529),3f));
                break;
            case "Africa":
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.655214, 21.771296),2.5f));
                break;
            case "Asia":
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.101502, 93.665827),1.5f));
                break;
            case "Australia":
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-28.242809, 148.740250),3));
                break;
            default:
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(1),5000,null);
                break;
        }
    }

    @Override
    public void onMapLoaded() {

        List<Double> lats = table.getLat(cont);
        List<Double> lngs = table.getLng(cont);
        List<String> plcs = table.getPlace(cont);

        for (int i = 0 ; i < lats.size(); i++){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lats.get(i), lngs.get(i)))
                    .title(plcs.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
            @Override
            public void onInfoWindowClick(Marker marker) {
                String place = marker.getTitle();
                Intent detailsIntent = new Intent(MapActivity.this,DetailsActivity.class);
                detailsIntent.putExtra("place",place);
                startActivity(detailsIntent);
            }
        });
    }
}

