package com.example.weather.domain;

import com.example.weather.data.WeatherApi;
import com.example.weather.data.WeatherProvider;
import com.example.weather.domain.entities.DetailedWeather;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GetCurrentWeatherInteractorImpl implements GetCurrentWeatherInteractor {

    WeatherProvider weatherProvider;

    public GetCurrentWeatherInteractorImpl(WeatherProvider weatherProvider) {
        this.weatherProvider = weatherProvider;
    }

    @Override
    public Observable<DetailedWeather> requestWeather() {
        return weatherProvider.getWeather(WeatherApi.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true);
    }
}
