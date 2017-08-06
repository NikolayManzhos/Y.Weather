package com.example.weather.data.network;

import com.example.weather.data.entities.weather.ForecastWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast/daily?cnt=16")
    Single<ForecastWeather> getCurrentWeather(
            @Query("lat") float latitude,
            @Query("lon") float longitude,
            @Query("appid") String key);
}
