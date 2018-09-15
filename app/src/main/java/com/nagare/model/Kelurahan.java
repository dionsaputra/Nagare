package com.nagare.model;

import com.google.android.gms.maps.model.LatLng;

public class Kelurahan {
    private String key, name;
    private LatLng position;

    public Kelurahan() {
    }

    public Kelurahan(String key, String name, LatLng position) {
        this.key = key;
        this.name = name;
        this.position = position;
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

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
