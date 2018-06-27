package com.codeforireland.vacanthome.utils;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Nikodem Walicki on 2018-06-10.
 */

public class MyLocationListener  implements LocationListener {

    private final static String TAG=MyLocationListener.class.getSimpleName();
    private double myLongitude, myLatitude;

    @Override
    public void onLocationChanged(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        Log.d(TAG, "onLocationChanged, lat: "+myLatitude+" lng: "+myLongitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "status changed, provider: "+provider+" status: "+status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "provider enabled: "+provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}