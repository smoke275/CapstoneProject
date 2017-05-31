package com.smokescreem.shash.foodscout.utils.api;

import com.smokescreem.shash.foodscout.utils.apimodel.Place;
import com.smokescreem.shash.foodscout.utils.apimodel.Restaurant;
import com.smokescreem.shash.foodscout.utils.apimodel.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Shash on 5/28/2017.
 */

public interface PlacesApi {
    @GET("details/json")
    Call<Review.Response> getReviews(@Query("placeid") String placeid, @Query("key") String apiKey);

    @GET("nearbysearch/json")
    Call<Place.Response> getPlace(@Query("location") String location, @Query("radius") String radius, @Query("key") String apiKey);

    @GET("nearbysearch/json?type=restaurant|cafe&rankby=prominence&radius=10000")
    Call<Restaurant.Response> getRestaurants(@Query("location") String location, @Query("key") String apiKey);
}