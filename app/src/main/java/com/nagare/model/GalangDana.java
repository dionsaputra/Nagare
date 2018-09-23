package com.nagare.model;

public class GalangDana {

    private String userKey, key, title, description;
    private Long targetDana, currentDana, limitWaktu;

    public GalangDana() {
    }

    public GalangDana(String title, String description, Long targetDana, Long limitWaktu) {
        this.title = title;
        this.description = description;
        this.targetDana = targetDana;
        this.limitWaktu = limitWaktu;
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

    public Long getTargetDana() {
        return targetDana;
    }

    public void setTargetDana(Long targetDana) {
        this.targetDana = targetDana;
    }

    public Long getCurrentDana() {
        return currentDana;
    }

    public void setCurrentDana(Long currentDana) {
        this.currentDana = currentDana;
    }

    public Long getLimitWaktu() {
        return limitWaktu;
    }

    public void setLimitWaktu(Long limitWaktu) {
        this.limitWaktu = limitWaktu;
    }
}
