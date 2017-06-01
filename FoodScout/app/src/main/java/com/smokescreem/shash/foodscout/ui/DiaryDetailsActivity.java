package com.smokescreem.shash.foodscout.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.smokescreem.shash.foodscout.R;
import com.smokescreem.shash.foodscout.data.DiaryColumns;
import com.smokescreem.shash.foodscout.data.DiaryData;
import com.smokescreem.shash.foodscout.data.DiaryProvider;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Shash on 5/20/2017.
 */

public class DiaryDetailsActivity extends AppCompatActivity {

    private static final String TAG = "DiaryDetailsActivity";
    private static boolean isEditable = false;
    private static KeyListener headerListener, bodyListener;
    @BindView(R.id.header)
    TextView header;
    @BindView(R.id.body)
    TextView body;
    private int mode;
    private DiaryData data;

    private void updateMemory() {

        String id = data.getId();
        ContentValues values = new ContentValues();
        values.put(DiaryColumns.BODY, body.getText().toString());
        values.put(DiaryColumns.HEADER, header.getText().toString());
        values.put(DiaryColumns.DATE, Calendar.getInstance().getTime().toString());
        getContentResolver().update(DiaryProvider.Memories.CONTENT_URI, values, DiaryColumns.ID + "=?", new String[]{id});
    }

    private void createNewMemory() {
        String id = Double.toString(System.currentTimeMillis() / 1000);
        ContentValues values = new ContentValues();
        values.put(DiaryColumns.ID, id);
        values.put(DiaryColumns.BODY, body.getText().toString());
        values.put(DiaryColumns.HEADER, header.getText().toString());
        values.put(DiaryColumns.DATE, Calendar.getInstance().getTime().toString());
        Uri uri = getContentResolver().insert(DiaryProvider.Memories.CONTENT_URI, values);
        data = new DiaryData(id,
                Calendar.getInstance().getTime().toString(),
                header.getText().toString(),
                body.getText().toString(),
                null,
                null);
        mode = 2;
        Log.d(TAG, "createNewMemory: " + uri.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_details);
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
            data = (DiaryData) getIntent().getSerializableExtra("data");
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
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.baseview),
                                R.string.saving, Snackbar.LENGTH_LONG);
                        mySnackbar.show();
                    } else {
                        updateMemory();
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.baseview),
                                R.string.saving, Snackbar.LENGTH_LONG);
                        mySnackbar.setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                header.setText(data.getHeader());
                                body.setText(data.getBody());
                                updateMemory();
                            }
                        });
                        mySnackbar.show();
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
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.baseview),
                            R.string.in_edit_mode, Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                }
                isEditable = !isEditable;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}