package com.nagare.model;

public class GalangDana {
    public String title, description, owner;
    public Long targetDana, currentDana, limitWaktu;

    public GalangDana(String title, String description, String owner, Long targetDana, Long currentDana, Long limitWaktu) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.targetDana = targetDana;
        this.currentDana = currentDana;
        this.limitWaktu = limitWaktu;
    }
}
