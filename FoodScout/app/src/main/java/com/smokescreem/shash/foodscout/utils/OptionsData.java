package com.smokescreem.shash.foodscout.utils;

/**
 * Created by Shash on 5/20/2017.
 */

public class OptionsData {

    String optionName;
    int imageResource;

    public OptionsData(String optionName, int imageResource) {
        this.optionName = optionName;
        this.imageResource = imageResource;
    }

    public String getOptionName() {
        return optionName;
    }

    public int getImageResource() {
        return imageResource;
    }
}