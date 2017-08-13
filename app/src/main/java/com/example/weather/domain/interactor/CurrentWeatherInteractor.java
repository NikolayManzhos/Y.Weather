package com.example.weather.domain.interactor;


import com.example.weather.domain.models.ForecastModel;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface CurrentWeatherInteractor {
    Observable<ForecastModel> requestWeather(boolean force, boolean checkForCityChange);
    Completable addToFavorites();
    Completable removeFromFavorites();
    Single<Boolean> checkCurrentPlaceInFavorites();
}
