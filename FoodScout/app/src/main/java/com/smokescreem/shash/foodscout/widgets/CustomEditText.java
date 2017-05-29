package com.smokescreem.shash.foodscout.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by Shash on 5/20/2017.
 */

public class CustomEditText extends AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
        setFontFace(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontFace(context);
        setBackgroundTransparent(context);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontFace(context);
        setBackgroundTransparent(context);
    }

    private void setFontFace(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Kingthings_Calligraphica_Italic.ttf"));
    }

    private void setBackgroundTransparent(Context context) {
        this.setBackgroundColor(Color.TRANSPARENT);
    }
}