package com.nagare.model;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Date;

public class Acara {
    public String title;
    public String description;
    public String userKey;
    public String key;
    public long date;

    public Acara() {

    }

    public Acara(String title, String description, String userKey, String key, long date) {
        this.title = title;
        this.description = description;
        this.userKey = userKey;
        this.key = key;
        this.date = date;
    }
}
