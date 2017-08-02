package com.example.weather.presentation.main.home_screen;


import com.example.weather.R;
import com.example.weather.domain.CurrentWeatherInteractor;
import com.example.weather.presentation.di.main_activity_component.home_component.HomeScope;
import com.example.weather.presentation.main.common.BaseMainPresenter;

import javax.inject.Inject;


@HomeScope
public class HomePresenter extends BaseMainPresenter<HomeView> {
    public static final String TAG = "tag_home_presenter";

    private CurrentWeatherInteractor currentWeatherInteractor;

    @Inject
    public HomePresenter(CurrentWeatherInteractor currentWeatherInteractor) {
        this.currentWeatherInteractor = currentWeatherInteractor;
    }

    public void getCurrentWeather(boolean force) {
        if (getView() != null) {
            getView().showLoad();
        }
        currentWeatherInteractor.requestWeather(force).subscribe(
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
    public void onAttach() {
        getCurrentWeather(false);
    }

    @Override
    public void onDetach() {}
}
