package com.smokescreem.shash.foodscout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.utils.Constants;
import com.smokescreem.shash.foodscout.utils.Coordinate;
import com.smokescreem.shash.foodscout.utils.HttpAsyncTask;
import com.smokescreem.shash.foodscout.utils.MenuAdapter;
import com.smokescreem.shash.foodscout.utils.MenuData;
import com.smokescreem.shash.foodscout.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shash on 5/20/2017.
 */

public class MenuActivity extends AppCompatActivity {

    private static String TAG = "MenuActivity";
    @BindView(R.id.title_menu)
    TextView title;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_menu)
    RecyclerView recyclerView;
    private Coordinate coordinate;
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
        coordinate = (Coordinate) intent.getSerializableExtra("coordinate");
        title.setText(mode);
        Log.d(TAG, "onCreate: " + coordinate.getPlaceID());
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
            String params = "";
            if (mode.equalsIgnoreCase(getResources().getString(R.string.places))) {
                for (String parameter : Constants.PLACES_OF_INTEREST) {
                    params += parameter + "|";
                }
            } else if (mode.equalsIgnoreCase(getResources().getString(R.string.restaurant))) {
                params = "restaurant|cafe";
            } else if (mode.equalsIgnoreCase(getResources().getString(R.string.hotels))) {
                params = "lodging";
            }
            params = params.substring(0, params.length() - 1);
            StringBuilder query = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            query.append("location=")
                    .append(coordinate.getLatitude())
                    .append(",")
                    .append(coordinate.getLongitude());
            query.append("&type=").append(params);
            query.append("&rankby=prominence");
            query.append("&radius=10000");
            query.append("&key=" + Constants.API_KEY);
            Log.d(TAG, "getPlaces: " + query);
            final HttpAsyncTask asyncTask = new HttpAsyncTask(new HttpAsyncTask.OnFinish() {
                @Override
                public void processData(JSONArray array, JSONObject object) {
                    List<MenuData> data = Utils.parseData(array);
                    Log.d(TAG, "processData: " + data.size());
                    MenuAdapter adapter = new MenuAdapter(data, new MenuAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(MenuData data) {
                            Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                            intent.putExtra("data", data);
                            intent.putExtra("coordinate", coordinate);
                            startActivity(intent);
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                }
            }, this);
            asyncTask.execute(query.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}