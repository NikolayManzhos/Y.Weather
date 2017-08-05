package com.example.weather.domain.interactor;


import com.example.weather.domain.models.CurrentWeather;

import io.reactivex.Observable;

public interface CurrentWeatherInteractor {
    Observable<CurrentWeather> requestWeather(boolean force);
}
