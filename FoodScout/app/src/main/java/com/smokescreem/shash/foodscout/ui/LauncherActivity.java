package com.smokescreem.shash.foodscout.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.utils.Constants;
import com.smokescreem.shash.foodscout.utils.Coordinate;
import com.smokescreem.shash.foodscout.utils.Utils;
import com.smokescreem.shash.foodscout.utils.api.PlacesApi;
import com.smokescreem.shash.foodscout.utils.api.PlacesApiClient;
import com.smokescreem.shash.foodscout.utils.apimodel.Place;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shash on 5/20/2017.
 */

public class LauncherActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "LauncherActivity";
    private static final int REQUEST_CODE = 10;
    private Intent intent;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected: Connected");
        mLocationRequest = createLocationRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }
        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @NonNull
    private LocationRequest createLocationRequest() {
        return new LocationRequest()
                .setFastestInterval(5000)
                .setInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed" + connectionResult);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged: " + location.getLatitude() + " " + location.getLongitude());
        intent = new Intent(this, MainActivity.class);
        Coordinate locale = new Coordinate(location.getLatitude(), location.getLongitude());
        setLocation(locale);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            locale.setName(addressList.get(0).getAddressLine(2));
        } catch (IOException e) {
            Utils.showToast(getResources().getString(R.string.error), this);
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    } else {
                        Log.d(TAG, "onRequestPermissionsResult: Location is required");
                        Utils.showToast(getResources().getString(R.string.required), getApplicationContext());
                    }
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Location is required");
                    Utils.showToast(getResources().getString(R.string.required), getApplicationContext());
                    finish();
                }

        }
    }


    private void setLocation(final Coordinate location) {

        PlacesApi placesAPI = PlacesApiClient.getClient().create(PlacesApi.class);

        Call<Place.Response> reviewCall = placesAPI.getPlace(location.getLatitude() + "," + location.getLongitude(), Constants.DEFAULT_RADIUS, Constants.API_KEY);
        Log.e(TAG, reviewCall.request().toString());
        reviewCall.enqueue(new Callback<Place.Response>() {
            @Override
            public void onResponse(Call<Place.Response> call, Response<Place.Response> response) {
                List<Place> places = response.body().places;
                if(places.size()>0){
                    location.setPlaceID(places.get(0).getPlaceId());
                    Log.d(TAG, "processData: " + location.getPlaceID());
                    List<Place.Photo> photos = places.get(0).getPhotos();
                    if(photos.size()>0){
                        Log.d(TAG, "processData: " + photos.get(0).getPhotoReference());
                        location.setPhotoReference(photos.get(0).getPhotoReference());
                    }
                    intent.putExtra("coordinate", location);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Place.Response> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }
}