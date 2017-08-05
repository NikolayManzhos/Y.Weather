package com.example.weather.presentation.main.home_screen;

import com.example.weather.domain.models.ForecastModel;
import com.example.weather.presentation.main.common.BaseMainView;

public interface HomeView extends BaseMainView {
    void showWeather(ForecastModel forecastModel);
}
