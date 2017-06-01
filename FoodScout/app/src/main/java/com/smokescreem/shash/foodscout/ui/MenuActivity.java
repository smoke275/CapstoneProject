package com.smokescreem.shash.foodscout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crystal.crystalpreloaders.widgets.CrystalPreloader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.utils.RestaurantCoordinate;
import com.smokescreem.shash.foodscout.utils.MenuAdapter;
import com.smokescreem.shash.foodscout.utils.MenuData;
import com.smokescreem.shash.foodscout.utils.Utils;
import com.smokescreem.shash.foodscout.utils.api.PlacesApi;
import com.smokescreem.shash.foodscout.utils.api.PlacesApiClient;
import com.smokescreem.shash.foodscout.utils.apimodel.Restaurant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shash on 5/20/2017.
 */

public class MenuActivity extends AppCompatActivity {

    private static String TAG = "MenuActivity";
    @BindView(R.id.title_menu)
    TextView title;
    @BindView(R.id.progressBar)
    CrystalPreloader progressBar;
    @BindView(R.id.recycler_view_menu)
    RecyclerView recyclerView;
    private RestaurantCoordinate restaurantCoordinate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);


        ButterKnife.bind(this);
        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        Log.d(TAG, "onCreate: " + mode);
        restaurantCoordinate = (RestaurantCoordinate) intent.getSerializableExtra("restaurantCoordinate");
        title.setText(mode);
        Log.d(TAG, "onCreate: " + restaurantCoordinate.getPlaceID());
        progressBar.setVisibility(View.VISIBLE);
        boolean status = getPlaces(mode);
        Log.d(TAG, "onCreate: " + status);
        final int columnCount = getResources().getInteger(R.integer.option_column_count);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                columnCount,
                StaggeredGridLayoutManager.VERTICAL
        );
        recyclerView.setLayoutManager(layoutManager);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean getPlaces(String mode) {
        try {

            PlacesApi placesAPI = PlacesApiClient.getClient().create(PlacesApi.class);

            Call<Restaurant.Response> reviewCall = placesAPI.getRestaurants(restaurantCoordinate.getLatitude() + "," + restaurantCoordinate.getLongitude(), getResources().getString(R.string.google_places_API));
            Log.e(TAG, reviewCall.request().toString());
            reviewCall.enqueue(new Callback<Restaurant.Response>() {
                @Override
                public void onResponse(Call<Restaurant.Response> call, Response<Restaurant.Response> response) {
                    List<Restaurant> restaurants = response.body().restaurants;
                    List<MenuData> data = Utils.parseData(restaurants);
                    Log.d(TAG, "processData: " + data.size());
                    MenuAdapter adapter = new MenuAdapter(data, new MenuAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MenuData data) {
                            Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                            intent.putExtra("data", data);
                            intent.putExtra("restaurantCoordinate", restaurantCoordinate);
                            startActivity(intent);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<Restaurant.Response> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}