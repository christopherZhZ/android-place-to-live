package com.christopherzhz.placetoliveapp.common.models;

import android.location.Location;

public class RoomInfoRow {

    private String title;
    private String detail;
    private String price;
    private Location location; // ignore for now
    private int pictureId;

    public RoomInfoRow(String title, String detail, String price, Location location, int pictureId) {
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.location = location;
        this.pictureId = pictureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
