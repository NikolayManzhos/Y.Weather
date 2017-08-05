package com.example.weather.presentation.main.home_screen;

import com.example.weather.domain.models.CurrentWeather;
import com.example.weather.presentation.main.common.BaseMainView;

public interface HomeView extends BaseMainView {
    void showWeather(CurrentWeather currentWeather);
}
