package com.example.weather.presentation.main.home_screen;


import android.support.annotation.VisibleForTesting;

import com.example.weather.R;
import com.example.weather.domain.GetCurrentWeatherInteractor;
import com.example.weather.presentation.di.main_activity_component.home_component.HomeScope;
import com.example.weather.presentation.main.common.BaseMainPresenter;

import javax.inject.Inject;


@HomeScope
public class HomePresenter extends BaseMainPresenter<HomeView> {
    public static final String TAG = "tag_home_presenter";

    private GetCurrentWeatherInteractor getCurrentWeatherInteractor;

    @Inject
    public HomePresenter(GetCurrentWeatherInteractor getCurrentWeatherInteractor) {
        this.getCurrentWeatherInteractor = getCurrentWeatherInteractor;
    }

    @Override
    public void onAttach() {
        getWeather();
    }

    public void refreshweather() {
        getWeather();
    }

    @VisibleForTesting
    void getWeather() {
        if (getView() != null) {
            getView().showLoad();
        }
        getCurrentWeatherInteractor.requestWeather().subscribe(
                detailedWeather -> {
                    if (getView() != null) {
                        getView().showWeather(HomeViewModel.create(detailedWeather));
                        getView().hideLoad();
                    }
                }, throwable -> {
                    if (getView() != null) {
                        getView().showError(R.string.error);
                        getView().hideLoad();
                    }
                }
        );
    }

    @Override
    public void onDetach() {}
}
