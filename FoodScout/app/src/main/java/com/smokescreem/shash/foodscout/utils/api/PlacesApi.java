package com.smokescreem.shash.foodscout.utils.api;

import com.smokescreem.shash.foodscout.utils.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shash on 5/28/2017.
 */

public interface PlacesApi {
    @GET("details/json")
    Call<Review.Response> getReviews(@Path("placeid") String placeid, @Query("key") String apiKey);
}