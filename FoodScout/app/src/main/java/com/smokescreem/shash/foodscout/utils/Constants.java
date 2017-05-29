package com.smokescreem.shash.foodscout.utils;

import android.app.Application;

/**
 * Created by Shash on 5/20/2017.
 */

public class Constants extends Application {

    public static final int imageResolution = 800;
    public static final String[] PLACES_OF_INTEREST = {"amusement_park", "aquarium", "art_gallery", "casino", "church",
            "movie_theater", "museum", "night_club", "park", "shopping_mall", "stadium", "zoo"};
    public static final String photoBaseURL = "https://maps.googleapis.com/maps/api/place/photo";
    /**
     * Enter Google Places API key here:
     */
    public static String PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place";
    public static String API_KEY = "AIzaSyCDjxRJjtacS98bkeborirCEcM4LHamP8k";
}