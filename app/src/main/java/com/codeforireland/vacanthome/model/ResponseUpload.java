package com.codeforireland.vacanthome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUpload {

    @SerializedName("house_type")
    @Expose
    private String house_type;

    @SerializedName("activity_found")
    @Expose
    private String activity_found;

    @SerializedName("grass_overgrown")
    @Expose
    private String grass_overgrown;

    @SerializedName("windows_blocked")
    @Expose
    private String windows_blocked;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("comment")
    @Expose
    private String comment;


    public String getHouse_type () {
        return house_type;
    }

    public void setHouse_type (String house_type) {
        this.house_type = house_type;
    }

    public String getActivity_found () {
        return activity_found;
    }

    public void setActivity_found (String activity_found) {
        this.activity_found = activity_found;
    }

    public String getGrass_overgrown () {
        return grass_overgrown;
    }

    public void setGrass_overgrown (String grass_overgrown) {
        this.grass_overgrown = grass_overgrown;
    }

    public String getWindows_blocked () {
        return windows_blocked;
    }

    public void setWindows_blocked (String windows_blocked) {
        this.windows_blocked = windows_blocked;
    }

    public double getLongitude () {
        return longitude;
    }

    public void setLongitude (double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude () {
        return latitude;
    }

    public void setLatitude (double latitude) {
        this.latitude = latitude;
    }

    public String getComment () {
        return comment;
    }

    public void setComment (String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ClassPojo [house_type = "+house_type+", activity_found = "+activity_found+", grass_overgrown = "+grass_overgrown+", windows_blocked = "+windows_blocked+", longitude = "+longitude+", latitude = "+latitude+", comment = "+comment+"]";
    }
}