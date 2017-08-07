package com.example.weather.presentation.main.home_screen.view_model;


public class WeatherViewModel {
    private String condition;
    private String date;
    private int iconId;
    private String temperature;
    private String temperatureNight;
    private String windSpeed;
    private String humidity;
    private String pressure;

    public WeatherViewModel(String condition,
                            String date,
                            int iconId,
                            String temperature,
                            String temperatureNight,
                            String windSpeed,
                            String humidity,
                            String pressure) {
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

    public String getDate() {
        return date;
    }

    public int getIconId() {
        return iconId;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTemperatureNight() {
        return temperatureNight;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getPressure() {
        return pressure;
    }
}
