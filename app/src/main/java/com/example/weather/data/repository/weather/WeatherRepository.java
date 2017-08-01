package com.example.weather.data.repository.weather;

import com.example.weather.domain.entities.DetailedWeather;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface WeatherRepository {
    Observable<DetailedWeather> getWeather(boolean force);
}
