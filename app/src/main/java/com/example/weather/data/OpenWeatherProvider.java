package com.example.weather.data;

import com.example.weather.cache.CacheManager;
import com.example.weather.domain.entities.DetailedWeather;
import com.example.weather.cache.PreferencesManager;

import io.reactivex.Observable;

public class OpenWeatherProvider implements WeatherProvider {
    private static final String TAG = "OpenWeatherProvider";
    private PreferencesManager preferencesManager;
    private CacheManager cacheManager;
    private WeatherApi weatherApi;

    public OpenWeatherProvider(WeatherApi weatherApi,
                               CacheManager cacheManager,
                               PreferencesManager preferencesManager) {
        this.weatherApi = weatherApi;
        this.cacheManager = cacheManager;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public Observable<DetailedWeather> getWeather(String key) {
        float latitude = preferencesManager.getLatitude();
        float longitude = preferencesManager.getLongitude();
        Observable<DetailedWeather> observable = weatherApi.getCurrentWeather(latitude, longitude, key);
        Observable<DetailedWeather> cachedWeather = Observable.fromCallable(() -> cacheManager.getLastWeather());
        return Observable.mergeDelayError(cachedWeather, observable);
    }
}
