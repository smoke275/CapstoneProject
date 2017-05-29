package com.smokescreem.shash.foodscout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.utils.Constants;
import com.smokescreem.shash.foodscout.utils.Coordinate;
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
    private Coordinate coordinate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        coordinate = (Coordinate) getIntent().getSerializableExtra("coordinate");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(coordinate.getName());
        String backdropUrl = Constants.photoBaseURL + "?maxwidth=" + Constants.imageResolution
                + "&photoreference=" + coordinate.getPhotoReference()
                + "&key=" + Constants.API_KEY;
        Glide.with(this)
                .load(backdropUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                    intent.putExtra("coordinate", coordinate);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getBaseContext(), MemoryActivity.class);
                    intent.putExtra("coordinate", coordinate);
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