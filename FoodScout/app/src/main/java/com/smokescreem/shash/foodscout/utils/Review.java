package com.smokescreem.shash.foodscout.utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shash on 5/20/2017.
 */

public class Review {

    private String author;

    private String text;

    public Review(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public static final class Response {

        @Expose
        public int page;

        @Expose @SerializedName("total_pages")
        public int totalPages;

        @Expose @SerializedName("total_results")
        public int totalMovies;

        @Expose @SerializedName("results")
        public List<Review> reviews = new ArrayList<>();
    }

}