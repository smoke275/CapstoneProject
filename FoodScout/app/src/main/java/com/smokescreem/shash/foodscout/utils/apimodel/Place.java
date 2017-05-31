package com.smokescreem.shash.foodscout.utils.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shash on 5/30/2017.
 */

public class Place {

    @Expose @SerializedName("place_id")
    private String placeId;

    @Expose
    private List<Photo> photos;

    public String getPlaceId() {
        return placeId;
    }

    public Place setPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Place setPhotos(List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public static final class Response {
        @Expose @SerializedName("results")
        public List<Place> places = new ArrayList<>();
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
}
