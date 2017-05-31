package com.smokescreem.shash.foodscout.utils.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shash on 5/20/2017.
 */

public class Review {

    @Expose @SerializedName("author_name")
    private String author;

    @Expose
    private String text;

    public Review(String author, String text) {
        this.author = author;
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public Review setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getText() {
        return text;
    }

    public Review setText(String text) {
        this.text = text;
        return this;
    }

    public static final class Response {

        @Expose @SerializedName("result")
        public ReviewSection reviewSection;

        public static final class ReviewSection {
            @Expose @SerializedName("reviews")
            public List<Review> reviews = new ArrayList<>();
        }
    }



}