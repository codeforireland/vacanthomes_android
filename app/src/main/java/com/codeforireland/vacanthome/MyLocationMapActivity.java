package com.codeforireland.vacanthome;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.codeforireland.vacanthome.FragmentSecondStepAddress.MAP_REQUEST;

public class MyLocationMapActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
//        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnMapClickListener,
        OnMapReadyCallback {

    private static final String TAG = MyLocationMapActivity.class.getSimpleName();
    private GoogleMap mMap;
    private LatLng currentLatLng;
    private FloatingActionButton btnFabFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location_map);
        if(getIntent().getExtras()!=null){
               currentLatLng = new LatLng(getIntent().getDoubleExtra("lat",0), getIntent().getDoubleExtra("lng",0));
            Log.d(TAG, "bundle location Lat:"+getIntent().getDoubleExtra("lat", 0));
        }

        btnFabFav = findViewById(R.id.activity_mylocation_mapa_btn_fab);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
//        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(this);
        if(currentLatLng!=null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16.0f));
        }
        btnFabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewLocation(currentLatLng);
            }
        });
    }

//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Log.d(TAG, "cliked at location: "+location);
//        Intent resultIntent = new Intent();
//        resultIntent.putExtra("lat",location.getLatitude());
//        resultIntent.putExtra("lng", location.getLongitude());
//        setResult(MAP_REQUEST, resultIntent);
//        finish();
//    }


    @Override
    public boolean onMyLocationButtonClick() {
        // the camera animates to the user's current position
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, "cliked at location: "+latLng);
        currentLatLng = latLng;
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

    }

    private void setNewLocation(LatLng latLng){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("lat",latLng.latitude);
        resultIntent.putExtra("lng", latLng.longitude);
        setResult(MAP_REQUEST, resultIntent);
        finish();
    }

}
