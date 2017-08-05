package com.example.weather.data.repository.weather;

import com.example.weather.BuildConfig;
import com.example.weather.data.local.CacheManager;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.network.WeatherApi;
import com.example.weather.data.entities.weather.DetailedWeather;

import io.reactivex.Single;

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
    public Single<DetailedWeather> getWeather(boolean force) {
        float latitude = preferencesManager.getCurrentLatitude();
        float longitude = preferencesManager.getCurrentLongitude();
        Single<DetailedWeather> networkWeather =
                weatherApi.getCurrentWeather(latitude, longitude, BuildConfig.WEATHER_KEY)
                .doOnSuccess(cacheManager::saveWeather);
        Single<DetailedWeather> cachedWeather =
                Single.fromCallable(() -> cacheManager.getLastWeather())
                        .onErrorReturnItem(new DetailedWeather());

        return force ? networkWeather : Single.concat(cachedWeather, networkWeather)
                .filter(detailedWeather -> detailedWeather.getBase() != null).firstOrError();
    }

}
