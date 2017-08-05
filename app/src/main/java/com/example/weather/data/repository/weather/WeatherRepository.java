package com.example.weather.data.repository.weather;

import com.example.weather.domain.models.ForecastModel;

import io.reactivex.Single;

public interface WeatherRepository {
    Single<ForecastModel> getWeather(boolean force);
}
