package com.nagare.model;

import com.google.android.gms.maps.model.LatLng;

public class Fasilitas {
    private String title, description, owner;
    private LatLng position;

    public Fasilitas() {}

    public Fasilitas(String title, String description, String owner, LatLng position) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
