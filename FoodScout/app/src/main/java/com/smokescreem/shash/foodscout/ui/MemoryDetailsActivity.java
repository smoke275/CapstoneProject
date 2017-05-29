package com.smokescreem.shash.foodscout.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.data.MemoryColumns;
import com.smokescreem.shash.foodscout.data.MemoryData;
import com.smokescreem.shash.foodscout.data.MemoryProvider;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shash on 5/20/2017.
 */

public class MemoryDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MemoryDetailsActivity";
    private static boolean isEditable = false;
    private static KeyListener headerListener, bodyListener;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.body)
    TextView body;
    private int mode;
    private MemoryData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_details);
        mode = getIntent().getIntExtra("mode", 1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        headerListener = header.getKeyListener();
        bodyListener = body.getKeyListener();
        body.setKeyListener(null);
        header.setKeyListener(null);
        Intent intent = getIntent();
        if (mode == 2) {
            data = (MemoryData) getIntent().getSerializableExtra("data");
            header.setText(data.getHeader());
            body.setText(data.getBody());
        }
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditable) {
                    //save data
                    if (mode == 1) {
                        createNewMemory();
                    } else {
                        updateMemory();
                    }
                    body.setKeyListener(null);
                    header.setKeyListener(null);
                    fab.setImageDrawable(getDrawable(R.drawable.ic_mode_edit_white_24dp));
                } else {
                    body.setKeyListener(bodyListener);
                    header.setKeyListener(headerListener);
                    header.setInputType(InputType.TYPE_CLASS_TEXT);
                    body.setInputType(InputType.TYPE_CLASS_TEXT);
                    fab.setImageDrawable(getDrawable(R.drawable.ic_save_white_24dp));
                }
                isEditable = !isEditable;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateMemory() {

        String id = data.getId();
        ContentValues values = new ContentValues();
        values.put(MemoryColumns.BODY, body.getText().toString());
        values.put(MemoryColumns.HEADER, header.getText().toString());
        values.put(MemoryColumns.DATE, Calendar.getInstance().getTime().toString());
        getContentResolver().update(MemoryProvider.Memories.CONTENT_URI, values, MemoryColumns.ID + "=?", new String[]{id});
    }

    private void createNewMemory() {
        String id = Double.toString(System.currentTimeMillis() / 1000);
        ContentValues values = new ContentValues();
        values.put(MemoryColumns.ID, id);
        values.put(MemoryColumns.BODY, body.getText().toString());
        values.put(MemoryColumns.HEADER, header.getText().toString());
        values.put(MemoryColumns.DATE, Calendar.getInstance().getTime().toString());
        Uri uri = getContentResolver().insert(MemoryProvider.Memories.CONTENT_URI, values);
        Log.d(TAG, "createNewMemory: " + uri.toString());
    }

}