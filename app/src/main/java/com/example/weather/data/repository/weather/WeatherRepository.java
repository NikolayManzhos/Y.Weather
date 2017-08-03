package com.example.weather.data.repository.weather;

import com.example.weather.domain.entities.weather.DetailedWeather;

import io.reactivex.Single;

public interface WeatherRepository {
    Single<DetailedWeather> getWeather(boolean force);
}
