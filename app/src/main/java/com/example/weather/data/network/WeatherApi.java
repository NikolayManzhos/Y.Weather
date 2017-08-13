package com.example.weather.data.network;

import com.example.weather.data.entities.weather.ForecastWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast/daily?cnt=16")
    Single<ForecastWeather> getCurrentWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String key);
}
