package com.example.weather.presentation.main;

import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;

public interface MainRouter {
    void showHomeScreen();
    void showSuggestScreen();
    void showSettingsScreen();
    void showAboutApplicationScreen();
    void showDetailsScreen(WeatherViewModel weatherViewModel);
}
