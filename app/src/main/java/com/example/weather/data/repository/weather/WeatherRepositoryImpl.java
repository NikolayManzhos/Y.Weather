package com.example.weather.data.repository.weather;

import com.example.weather.data.local.CacheManager;
import com.example.weather.data.WeatherApi;
import com.example.weather.domain.entities.DetailedWeather;
import com.example.weather.data.local.PreferencesManager;

import io.reactivex.Observable;

public class WeatherRepositoryImpl implements WeatherRepository {
    private PreferencesManager preferencesManager;
    private CacheManager cacheManager;
    private WeatherApi weatherApi;

    public WeatherRepositoryImpl(WeatherApi weatherApi,
                                 CacheManager cacheManager,
                                 PreferencesManager preferencesManager) {
        this.weatherApi = weatherApi;
        this.cacheManager = cacheManager;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public Observable<DetailedWeather> getWeather(boolean force) {
        float latitude = preferencesManager.getLatitude();
        float longitude = preferencesManager.getLongitude();
        Observable<DetailedWeather> networkWeather =
                weatherApi.getCurrentWeather(latitude, longitude, WeatherApi.API_KEY)
                .doOnNext(cacheManager::saveWeather);
        Observable<DetailedWeather> cachedWeather =
                Observable
                        .fromCallable(() -> cacheManager.getLastWeather())
                        .onErrorReturnItem(new DetailedWeather());

        return force ? networkWeather : Observable.concat(cachedWeather, networkWeather)
                .filter(detailedWeather -> detailedWeather.getBase() != null).firstOrError().toObservable();
    }
}
