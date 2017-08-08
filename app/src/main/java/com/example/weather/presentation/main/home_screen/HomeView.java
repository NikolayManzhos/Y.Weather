package com.example.weather.presentation.main.home_screen;

import com.example.weather.presentation.main.common.BaseMainView;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;

public interface HomeView extends BaseMainView {
    void showWeather(HomeViewModel viewModel);
}
