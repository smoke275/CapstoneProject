package com.smokescreem.shash.foodscout.utils.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shash on 5/30/2017.
 */

public class Restaurant {

    @Expose @SerializedName("place_id")
    private String placeId;

    @Expose
    private String name;

    @Expose
    private String vicinity;

    @Expose
    private List<Photo> photos;

    @Expose @SerializedName("opening_hours")
    private OpeningHours openingHours;

    @Expose
    private Geometry geometry;

    @Expose
    private double rating;

    public String getPlaceId() {
        return placeId;
    }

    public Restaurant setPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public String getVicinity() {
        return vicinity;
    }

    public Restaurant setVicinity(String vicinity) {
        this.vicinity = vicinity;
        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;
        return this;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Restaurant setPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public Restaurant setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
        return this;
    }


    public Geometry getGeometry() {
        return geometry;
    }

    public Restaurant setGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public Restaurant setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public static final class Response {
        @Expose
        @SerializedName("results")
        public List<Restaurant> restaurants = new ArrayList<>();
    }

    public static final class Photo {

        @Expose @SerializedName("photo_reference")
        private String photoReference;

        public String getPhotoReference() {
            return photoReference;
        }

        public Photo setPhotoReference(String photoReference) {
            this.photoReference = photoReference;
            return this;
        }
    }
    public static final class OpeningHours {

        @Expose @SerializedName("open_now")
        private boolean openNow;

        public boolean getOpenNow() {
            return openNow;
        }

        public OpeningHours setOpenNow(boolean openNow) {
            this.openNow = openNow;
            return this;
        }
    }
    public static final class Geometry {

        @Expose @SerializedName("location")
        private Location location;

        public Location getLocation() {
            return location;
        }

        public Geometry setLocation(Location location) {
            this.location = location;
            return this;
        }
        public static final class Location {

            @Expose
            private double lat;

            @Expose
            private double lng;


            public double getLat() {
                return lat;
            }

            public Location setLat(double lat) {
                this.lat = lat;
                return this;
            }

            public double getLng() {
                return lng;
            }

            public Location setLng(double lng) {
                this.lng = lng;
                return this;
            }
        }
    }
}
