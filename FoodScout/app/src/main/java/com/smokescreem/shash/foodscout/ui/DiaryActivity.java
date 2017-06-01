package com.smokescreem.shash.foodscout.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.data.DiaryColumns;
import com.smokescreem.shash.foodscout.utils.RestaurantCoordinate;
import com.smokescreem.shash.foodscout.utils.MemoryAdapter;
import com.smokescreem.shash.foodscout.data.DiaryData;
import com.smokescreem.shash.foodscout.data.DiaryProvider;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shash on 5/20/2017.
 */

public class DiaryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "DiaryActivity";
    private static final int URL_LOADER = 0;
    @BindView(R.id.memory_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fab_memory)
    FloatingActionButton memoryAdd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    RestaurantCoordinate restaurantCoordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.memory_collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.memory_title));
        ImageView backdrop = (ImageView) findViewById(R.id.memory_backdrop);
        Glide.with(this)
                .load(R.drawable.memory)
                .into(backdrop);
        final int columnCount = getResources().getInteger(R.integer.option_column_count);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                columnCount,
                StaggeredGridLayoutManager.VERTICAL
        );
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        restaurantCoordinate = (RestaurantCoordinate) intent.getSerializableExtra("restaurantCoordinate");

        memoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), DiaryDetailsActivity.class);
                intent.putExtra("mode", 1);
                startActivity(intent);
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMemories();
    }

    private void getMemories() {

        getSupportLoaderManager().initLoader(URL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, DiaryProvider.Memories.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        cursor.moveToFirst();
        ArrayList<DiaryData> memoryList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            Log.d(TAG, "getMemories: " + Arrays.toString(cursor.getColumnNames()));
            DiaryData data = new DiaryData(
                    cursor.getString(cursor.getColumnIndex(DiaryColumns.ID)),
                    cursor.getString(cursor.getColumnIndex(DiaryColumns.DATE)),
                    cursor.getString(cursor.getColumnIndex(DiaryColumns.HEADER)),
                    cursor.getString(cursor.getColumnIndex(DiaryColumns.BODY)),
                    cursor.getString(cursor.getColumnIndex(DiaryColumns.LATITUDE)),
                    cursor.getString(cursor.getColumnIndex(DiaryColumns.LONGITUDE))
            );
            memoryList.add(data);
            cursor.moveToNext();
        }
        MemoryAdapter adapter = new MemoryAdapter(memoryList, new MemoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DiaryData data) {
                Log.d(TAG, "onItemClick: " + data.getHeader());
                Log.d(TAG, "onItemClick: " + data.getBody() + " " + data.getId());
                Intent intent = new Intent(getBaseContext(), DiaryDetailsActivity.class);
                intent.putExtra("mode", 2);
                intent.putExtra("data", data);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}