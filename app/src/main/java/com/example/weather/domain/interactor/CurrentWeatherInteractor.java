package com.example.weather.domain.interactor;


import com.example.weather.domain.entities.weather.DetailedWeather;

import io.reactivex.Observable;

public interface CurrentWeatherInteractor {
    Observable<DetailedWeather> requestWeather(boolean force);
}
