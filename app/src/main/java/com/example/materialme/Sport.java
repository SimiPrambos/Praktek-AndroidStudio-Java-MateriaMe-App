package com.example.materialme;

public class Sport {

    private String title;
    private String info;


    Sport(String title, String info) {
        this.title = title;
        this.info = info;
    }

    String getTitle() {
        return title;
    }

    String getInfo() {
        return info;
    }
}
