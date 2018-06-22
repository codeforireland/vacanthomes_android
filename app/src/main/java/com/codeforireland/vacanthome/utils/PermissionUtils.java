package com.codeforireland.vacanthome.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nikodem Walicki on 2018-06-10.
 * TODO: Not used - delete it later
 */
public class PermissionUtils {

    private final static String TAG = PermissionUtils.class.getSimpleName();
    private Activity current_activity;
    private PermissionResultCallback permissionResultCallback;


    private ArrayList<String> permission_list=new ArrayList<>();
    private ArrayList<String> listPermissionsNeeded=new ArrayList<>();
    private String dialog_content="";
    private int req_code;

    public PermissionUtils(Context context) {
        this.current_activity= (Activity) context;
        permissionResultCallback= (PermissionResultCallback) context;
    }


    /**
     * Check the API Level & Permission
     *
     * @param permissions permissions list
     * @param dialog_content dialog text
     * @param request_code int
     */

    public void checkPermissionDialog(ArrayList<String> permissions, String dialog_content, int request_code)
    {
        this.permission_list=permissions;
        this.dialog_content=dialog_content;
        this.req_code=request_code;

        if(Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, request_code)) {
                permissionResultCallback.PermissionGranted(request_code);
                Log.i(TAG, "all permissions granted");
                Log.i(TAG, "proceed to callback");
            }
        }
        else {
            permissionResultCallback.PermissionGranted(request_code);

            Log.i(TAG, "all permissions granted");
            Log.i(TAG, "proceed to callback");
        }
    }
    public void checkPermission(ArrayList<String> permissions, int request_code)
    {
        this.permission_list=permissions;
        this.req_code=request_code;

        if(Build.VERSION.SDK_INT >= 23) {
            if (checkAndRequestPermissions(permissions, request_code)) {
                permissionResultCallback.PermissionGranted(request_code);
                Log.i(TAG, "all permissions granted");
                Log.i(TAG, "proceed to callback");
            }
        }
        else {
            permissionResultCallback.PermissionGranted(request_code);
            Log.i(TAG, "all permissions granted");
            Log.i(TAG, "proceed to callback");
        }
    }


    /**
     * Check and request the Permissions
     *
     * @param permissions list
     * @param request_code int
     * @return boolean
     */

    private  boolean checkAndRequestPermissions(ArrayList<String> permissions,int request_code) {

        if(permissions.size()>0)
        {
            listPermissionsNeeded = new ArrayList<>();
            for(int i=0;i<permissions.size();i++) {
                int hasPermission = ContextCompat.checkSelfPermission(current_activity,permissions.get(i));

                if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(permissions.get(i));
                }
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(current_activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),request_code);
                return false;
            }
        }

        return true;
    }


}
