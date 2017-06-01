package com.smokescreem.shash.foodscout.data;

import java.io.Serializable;

/**
 * Created by Shash on 5/20/2017.
 */

public class DiaryData implements Serializable {

    private String id, date, header, body, latitude, longitude;

    public DiaryData(String id, String date, String header, String body, String latitude, String longitude) {
        this.id = id;
        this.date = date;
        this.header = header;
        this.body = body;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}