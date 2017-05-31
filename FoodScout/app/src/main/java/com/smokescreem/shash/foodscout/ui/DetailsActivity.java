package com.smokescreem.shash.foodscout.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.apg.mobile.roundtextview.RoundTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hsalf.smilerating.SmileRating;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.utils.Constants;
import com.smokescreem.shash.foodscout.utils.Coordinate;
import com.smokescreem.shash.foodscout.utils.MenuData;
import com.smokescreem.shash.foodscout.utils.ReviewAdapter;
import com.smokescreem.shash.foodscout.utils.api.PlacesApi;
import com.smokescreem.shash.foodscout.utils.api.PlacesApiClient;
import com.smokescreem.shash.foodscout.utils.apimodel.Review;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shash on 5/20/2017.
 */

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "DetailsActivity";
    @BindView(R.id.navigate)
    br.com.bloder.magic.view.MagicButton navigate;
    @BindView(R.id.place_title)
    com.koonat.easyfont.TextView placeTitle;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.open)
    RoundTextView open;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.ratingBar)
    SmileRating ratings;
    @BindView(R.id.recycler_view_review)
    RecyclerView reviewRecyclerView;
    private Coordinate coordinate;
    private MenuData destinationData;
    private GoogleMap googleMap;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        Intent intent = getIntent();
        coordinate = (Coordinate) intent.getSerializableExtra("coordinate");
        destinationData = (MenuData) intent.getSerializableExtra("data");
        placeTitle.setText(destinationData.getTitle());
        address.setText(destinationData.getAddress());
        int rating = (int)destinationData.getRating();
        ratings.setSelectedSmile((rating==5)?4:rating);
        ratings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if (destinationData.isOpen()) {
            open.setText(R.string.open);
            open.setBgColor(ContextCompat.getColor(this, R.color.open));
        } else {
            open.setText(R.string.closed);
            open.setBgColor(ContextCompat.getColor(this, R.color.close));
        }
        collapsingToolbarLayout.setTitle(getString(R.string.details_head));
        collapsingToolbarLayout.setExpandedTitleColor(Color.alpha(0));
        String backdropUrl = Constants.photoBaseURL + "?maxwidth=" + Constants.imageResolution
                + "&photoreference=" + destinationData.getThumbnailReference()
                + "&key=" + Constants.API_KEY;
        Glide.with(this)
                .load(backdropUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                int defaultColor = 0xFF333333;
                                int darkMutedColor = palette.getDarkMutedColor(defaultColor);
                                titleLayout.setBackgroundColor(darkMutedColor);
                            }
                        });
                        return false;
                    }
                })
                .into(img);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                String uri = "http://maps.google.com/maps?daddr=" + destinationData.getLatitude() + "," + destinationData.getLongitude();
                shareIntent.setType("text/plain");
                String subject = getString(R.string.shareSubject);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                shareIntent.putExtra(Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        navigate.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + coordinate.getLatitude() + "," + coordinate.getLongitude()
                                + "&daddr=" + destinationData.getLatitude() + "," + destinationData.getLongitude()));
                startActivity(intent);
            }
        });
        Log.d(TAG, "onCreate: " + destinationData.getPlaceID());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setLayoutManager(layoutManager);
        getReviews();
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        final LatLng position = new LatLng(coordinate.getLatitude(), coordinate.getLongitude());
        final LatLng destination = new LatLng(destinationData.getLatitude(), destinationData.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14.0f));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        googleMap.addMarker(new MarkerOptions().position(destination).title(destinationData.getTitle()));
        GoogleDirection.withServerKey(getResources().getString(R.string.google_places_API))
                .from(position)
                .to(destination)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        Log.d(TAG, "onDirectionSuccess: " + rawBody);
                        if (direction.isOK()) {
                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            //noinspection deprecation
                            googleMap.addPolyline(DirectionConverter.createPolyline(getBaseContext(), directionPositionList, 5, getResources().getColor(R.color.accent)));
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            builder.include(position);
                            builder.include(destination);
                            LatLngBounds bounds = builder.build();
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 300));

                        } else {
                            if (getCurrentFocus() != null)
                                Snackbar.make(getCurrentFocus(), getResources().getString(R.string.error), Snackbar.LENGTH_LONG)
                                        .show();
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
    }

    private void getReviews() {

        PlacesApi placesAPI = PlacesApiClient.getClient().create(PlacesApi.class);

        Call<Review.Response> reviewCall = placesAPI.getReviews(destinationData.getPlaceID(), Constants.API_KEY);

        reviewCall.enqueue(new Callback<Review.Response>() {
            @Override
            public void onResponse(Call<Review.Response> call, Response<Review.Response> response) {
                List<Review> reviews = response.body().reviewSection.reviews;
                ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);
                reviewRecyclerView.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<Review.Response> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

}