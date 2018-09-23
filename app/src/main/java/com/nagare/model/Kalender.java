package com.nagare.model;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Date;

public class Kalender {
    public String title;
    public String description;
    public String userKey;
    public String key;
    public long date;

    public Kalender() {

    }

    public Kalender(String title, String description, String userKey, String key, long date) {
        this.title = title;
        this.description = description;
        this.userKey = userKey;
        this.key = key;
        this.date = date;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
