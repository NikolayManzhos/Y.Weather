package com.example.weather.presentation.main.home_screen.view_model;


import java.util.List;

public class HomeViewModel {

    private List<WeatherViewModel> forecast;
    private String cityName;

    public HomeViewModel(List<WeatherViewModel> forecast,
                          String cityName) {
        this.forecast = forecast;
        this.cityName = cityName;
    }


    public List<WeatherViewModel> getForecast() {
        return forecast;
    }

    public String getCityName() {
        return cityName;
    }
}
