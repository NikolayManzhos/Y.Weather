package com.example.weather.domain.interactor;


import com.example.weather.domain.models.ForecastModel;

import io.reactivex.Observable;

public interface CurrentWeatherInteractor {
    Observable<ForecastModel> requestWeather(boolean force);
}
