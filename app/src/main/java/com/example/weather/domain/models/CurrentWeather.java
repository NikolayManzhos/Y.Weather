package com.example.weather.domain.models;

public class CurrentWeather {

    private String cityName;
    private String condition;
    private int temperature;
    private int windSpeed;
    private int humidity;

    public CurrentWeather(String cityName,
                          String condition,
                          int temperature,
                          int windSpeed,
                          int humidity) {
        this.cityName = cityName;
        this.condition = condition;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
