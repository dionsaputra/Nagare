package com.nagare.model;

public class GalangDana {
    public String title;
    public String owner;
    public String description;
    public int imageResId;

    public GalangDana(String title, String owner, String description, int imageResId) {
        this.title = title;
        this.owner = owner;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }
    public String getOwner() {
        return owner;
    }
    public String getDescription() {
        return description;
    }
    public int getImageResId() {
        return imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
