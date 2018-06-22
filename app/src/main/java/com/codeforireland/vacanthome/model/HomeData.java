package com.codeforireland.vacanthome.model;

import android.graphics.Bitmap;

public class HomeData {

    private String homeType;
    private double latitude, longitude;
    private Bitmap photo;
    private boolean grassOverGrown, windowsBoarded, visibleActivity;
    private String comment;

    private HomeData(){}

    private static HomeData homeDataInstance;

    public static HomeData getHomeDataInstance(){
        if(null==homeDataInstance) homeDataInstance = new HomeData();
        return homeDataInstance;
    }

    public static void resetHomeData(){
        homeDataInstance=null;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public boolean isGrassOverGrown() {
        return grassOverGrown;
    }

    public void setGrassOverGrown(boolean grassOverGrown) {
        this.grassOverGrown = grassOverGrown;
    }

    public boolean isWindowsBoarded() {
        return windowsBoarded;
    }

    public void setWindowsBoarded(boolean windowsBoarded) {
        this.windowsBoarded = windowsBoarded;
    }

    public boolean isVisibleActivity() {
        return visibleActivity;
    }

    public void setVisibleActivity(boolean visibleActivity) {
        this.visibleActivity = visibleActivity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
