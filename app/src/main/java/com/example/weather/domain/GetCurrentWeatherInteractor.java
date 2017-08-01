package com.example.weather.domain;


import com.example.weather.domain.entities.DetailedWeather;

import io.reactivex.Observable;

public interface GetCurrentWeatherInteractor {
    Observable<DetailedWeather> requestWeather(boolean force);
}
