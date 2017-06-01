package com.smokescreem.shash.foodscout.utils;

import java.io.Serializable;

/**
 * Created by Shash on 5/20/2017.
 */

public class RestaurantCoordinate implements Serializable {

    private double latitude;
    private double longitude;
    private String placeID;
    private String name;
    private String photoReference;

    public RestaurantCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        placeID = "";
        name = "";
        photoReference = "";
    }

    public RestaurantCoordinate(RestaurantCoordinate restaurantCoordinate) {
        this.latitude = restaurantCoordinate.latitude;
        this.longitude = restaurantCoordinate.longitude;
        this.placeID = restaurantCoordinate.placeID;
        this.name = restaurantCoordinate.name;
        this.photoReference = restaurantCoordinate.photoReference;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}