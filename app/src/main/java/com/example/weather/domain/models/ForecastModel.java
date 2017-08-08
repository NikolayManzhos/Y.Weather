package com.example.weather.domain.models;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ForecastModel extends RealmObject {

    @PrimaryKey
    private String primaryKey;
    private String cityName;
    private RealmList<CurrentWeatherModel> currentWeatherModels;
    private double latitude;
    private double longitude;

    public ForecastModel() {}

    public ForecastModel(String cityName,
                         RealmList<CurrentWeatherModel> currentWeatherModels,
                         double latitude,
                         double longitude) {
        this.cityName = cityName;
        this.currentWeatherModels = currentWeatherModels;
        this.latitude = latitude;
        this.longitude = longitude;
        this.primaryKey = "forecast";
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public RealmList<CurrentWeatherModel> getCurrentWeatherModels() {
        return currentWeatherModels;
    }

    public String getCityName() {
        return cityName;
    }
}
