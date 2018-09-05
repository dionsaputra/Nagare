package com.nagare.model;

public class Acara {
    public String title;
    public String owner;
    public String description;
    public int imageResId;

    public Acara(String title, String owner, String description, int imageResId) {
        this.title = title;
        this.owner = owner;
        this.description = description;
        this.imageResId = imageResId;
    }
}
