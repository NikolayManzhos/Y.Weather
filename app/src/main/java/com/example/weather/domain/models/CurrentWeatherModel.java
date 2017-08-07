package com.example.weather.domain.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentWeatherModel extends RealmObject {

    private String condition;
    @PrimaryKey
    private int date;
    private int iconId;
    private double temperature;
    private double temperatureNight;
    private double windSpeed;
    private int humidity;
    private double pressure;

    public CurrentWeatherModel() {}

    public CurrentWeatherModel(String condition,
                               int date,
                               int iconId,
                               double temperature,
                               double temperatureNight,
                               double windSpeed,
                               int humidity,
                               double pressure) {
        this.condition = condition;
        this.date = date;
        this.iconId = iconId;
        this.temperature = temperature;
        this.temperatureNight = temperatureNight;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public String getCondition() {
        return condition;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperatureNight() {
        return temperatureNight;
    }

    public int getDate() {
        return date;
    }

    public int getIconId() {
        return iconId;
    }
}
