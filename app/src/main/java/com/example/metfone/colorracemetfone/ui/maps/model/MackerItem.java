package com.example.metfone.colorracemetfone.ui.maps.model;

public class MackerItem {
    private double latitude;
    private double longitude;
    private String title;
    private String id;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public MackerItem(double latitude, double longitude, String title, String id, String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.id = id;
        this.time = time;
    }
}
