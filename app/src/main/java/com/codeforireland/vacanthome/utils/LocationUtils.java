package com.codeforireland.vacanthome.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Nikodem Walicki on 2018-06-10.
 */
public class LocationUtils {

    private final static String TAG = LocationUtils.class.getSimpleName();

    public static Location getLatLng(Activity ac) throws SecurityException{
        Location l= null;
        LocationManager lm;
        MyLocationListener listener = new MyLocationListener();

        lm = (LocationManager) ac.getSystemService(Context.LOCATION_SERVICE);

        if(lm!=null){
            if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
                l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if(l==null){
                Log.d(TAG, "Location is null..");
                Toast.makeText(ac, "Location is NULL", Toast.LENGTH_SHORT).show();
            }
            lm.removeUpdates(listener);
        }else {
            Log.d(TAG, "bestProvider: NULL");
            Toast.makeText(ac, "Please TURN ON location on your phone", Toast.LENGTH_SHORT).show();
        }
        return l;
    }
}