package com.example.weather.data.local;

import com.example.weather.domain.entities.weather.DetailedWeather;

public interface CacheManager {
    void saveWeather(DetailedWeather weather);
    DetailedWeather getLastWeather();
}
