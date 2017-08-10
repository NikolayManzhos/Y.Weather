package com.example.weather.data.repository.weather;

import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.domain.models.ForecastModel;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface WeatherRepository {
    Single<ForecastModel> getWeather(boolean force);
    Completable writeCurrentPlaceToFavorites();
    Completable deleteCurrentPlaceFromFavorites();
    Single<Boolean> checkIsCurrentPlaceFavorite();
}
