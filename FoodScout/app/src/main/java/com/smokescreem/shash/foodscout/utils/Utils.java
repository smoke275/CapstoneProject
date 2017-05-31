package com.smokescreem.shash.foodscout.utils;

import android.content.Context;
import android.widget.Toast;

import com.smokescreem.shash.foodscout.utils.apimodel.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shash on 5/20/2017.
 */

public class Utils {

    private static String TAG = "Utils";

    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static List<MenuData> parseData(List<Restaurant> array) {
        List<MenuData> parsedList = new ArrayList<>();
        for(Restaurant restaurant: array){
            String name = restaurant.getName();
            boolean isOpen = false;
            if(restaurant.getOpeningHours()!=null)
                isOpen = restaurant.getOpeningHours().getOpenNow();
            String photoReference = "";
            try {
                photoReference = restaurant.getPhotos().get(0).getPhotoReference();
            } catch (Exception e) {
            }
            double rating = restaurant.getRating();
            String address = restaurant.getVicinity();
            double latitude = restaurant.getGeometry().getLocation().getLat();
            double longitude = restaurant.getGeometry().getLocation().getLng();
            String placeId = restaurant.getPlaceId();
            MenuData placeData = new MenuData(name, address, photoReference,
                    latitude, longitude, rating, isOpen, placeId);
            parsedList.add(placeData);
        }
        return parsedList;
    }

}