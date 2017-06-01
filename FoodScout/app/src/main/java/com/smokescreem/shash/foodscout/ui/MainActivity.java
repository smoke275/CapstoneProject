package com.smokescreem.shash.foodscout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.utils.Constants;
import com.smokescreem.shash.foodscout.utils.RestaurantCoordinate;
import com.smokescreem.shash.foodscout.utils.OptionsAdapter;
import com.smokescreem.shash.foodscout.utils.OptionsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.backdrop)
    ImageView cityBackdrop;
    @BindView(R.id.recycler_view)
    RecyclerView options;
    private RestaurantCoordinate restaurantCoordinate;
    @BindView(R.id.subtitle)
    TextView subtitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        restaurantCoordinate = (RestaurantCoordinate) getIntent().getSerializableExtra("restaurantCoordinate");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        String name[] = restaurantCoordinate.getName().split(",");
        collapsingToolbarLayout.setTitle(name[0]);
        subtitle.setText(name[1]);
        String backdropUrl = Constants.photoBaseURL + "?maxwidth=" + Constants.imageResolution
                + "&photoreference=" + restaurantCoordinate.getPhotoReference()
                + "&key=" + getResources().getString(R.string.google_places_API);
        Glide.with(this)
                .load(backdropUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .thumbnail(0.1f)
                .placeholder(getDrawable(R.drawable.placeholder))
                .into(cityBackdrop);
        final int columnCount = getResources().getInteger(R.integer.list_column_count);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                columnCount,
                StaggeredGridLayoutManager.VERTICAL
        );
        List<OptionsData> data = getData();
        OptionsAdapter adapter = new OptionsAdapter(data, new OptionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OptionsData data) {
                Log.d(TAG, "onItemClick: " + data.getOptionName());
                if (!data.getOptionName().equalsIgnoreCase("memories")) {
                    Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                    intent.putExtra("mode", data.getOptionName());
                    intent.putExtra("restaurantCoordinate", restaurantCoordinate);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), DiaryActivity.class);
                    intent.putExtra("restaurantCoordinate", restaurantCoordinate);
                    startActivity(intent);
                }
            }
        });
        options.setLayoutManager(layoutManager);
        options.setAdapter(adapter);
    }

    public List<OptionsData> getData() {
        List<OptionsData> data = new ArrayList<>();

        data.add(new OptionsData(getResources().getString(R.string.restaurant),
                R.drawable.restaurant));
        data.add(new OptionsData(getResources().getString(R.string.memories),
                R.drawable.memory));
        return data;
    }
}