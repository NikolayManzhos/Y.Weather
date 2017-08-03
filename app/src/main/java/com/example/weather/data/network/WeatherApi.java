package com.example.weather.data.network;

import com.example.weather.domain.entities.weather.DetailedWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Single<DetailedWeather> getCurrentWeather(
            @Query("lat") float latitude,
            @Query("lon") float longitude,
            @Query("appid") String key);
}
