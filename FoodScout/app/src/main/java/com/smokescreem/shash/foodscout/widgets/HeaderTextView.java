package com.smokescreem.shash.foodscout.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by Shash on 5/20/2017.
 */

public class HeaderTextView extends AppCompatTextView {


    public HeaderTextView(Context context) {
        super(context);
        setFontFace(context);
    }

    public HeaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontFace(context);
    }

    public HeaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontFace(context);
    }

    private void setFontFace(Context context) {
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "Kingthings_Calligraphica_Italic.ttf"));
    }
}