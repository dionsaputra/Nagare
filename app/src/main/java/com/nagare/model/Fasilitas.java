package com.nagare.model;

import com.google.android.gms.maps.model.LatLng;

public class Fasilitas {
    private String userKey, key, name, description;
    private double latitude, longitude;

    public Fasilitas() {}

    public Fasilitas (String name, String description, LatLng position) {
        this.name = name;
        this.description = description;
        this.latitude = position.latitude;
        this.longitude = position.longitude;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
