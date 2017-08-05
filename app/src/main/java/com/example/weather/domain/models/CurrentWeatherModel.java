package com.example.weather.domain.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CurrentWeatherModel extends RealmObject {

    private String condition;
    @PrimaryKey
    private int date;
    private int iconId;
    private int temperature;
    private int temperatureNight;
    private int windSpeed;
    private int humidity;
    private int pressure;

    public CurrentWeatherModel() {}

    public CurrentWeatherModel(String condition,
                               int date,
                               int iconId,
                               int temperature,
                               int temperatureNight,
                               int windSpeed,
                               int humidity,
                               int pressure) {
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

    public int getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public int getTemperatureNight() {
        return temperatureNight;
    }

    public int getDate() {
        return date;
    }

    public int getIconId() {
        return iconId;
    }
}
