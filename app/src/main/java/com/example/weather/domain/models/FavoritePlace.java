package com.example.weather.domain.models;


import io.realm.annotations.PrimaryKey;

import static java.lang.String.*;

public class FavoritePlace {

    @PrimaryKey
    private String primaryKey;
    private String name;
    private double latitude;
    private double longitude;

    public FavoritePlace(String name,
                         double latitude,
                         double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.primaryKey = valueOf(latitude) + valueOf(longitude);
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
