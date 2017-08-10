package com.example.weather.data.repository.weather;

import com.example.weather.BuildConfig;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.network.WeatherApi;
import com.example.weather.domain.ModelMapper;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.domain.models.ForecastModel;

import io.reactivex.Completable;
import io.reactivex.Single;

public class WeatherRepositoryImpl implements WeatherRepository {

    private WeatherApi weatherApi;
    private PreferencesManager preferencesManager;
    private RealmHelper realmHelper;
    private ModelMapper mapper;

    public WeatherRepositoryImpl(WeatherApi weatherApi,
                                 PreferencesManager preferencesManager,
                                 RealmHelper realmHelper,
                                 ModelMapper mapper) {
        this.weatherApi = weatherApi;
        this.preferencesManager = preferencesManager;
        this.realmHelper = realmHelper;
        this.mapper = mapper;
    }

    @Override
    public Single<ForecastModel> getWeather(boolean force) {
        float latitude = preferencesManager.getCurrentLatitude();
        float longitude = preferencesManager.getCurrentLongitude();
        Single<ForecastModel> networkWeather =
                weatherApi.getCurrentWeather(latitude, longitude, BuildConfig.WEATHER_KEY)
                        .map(mapper::entityToModel)
                        .doOnSuccess(realmHelper::writeForecast);
        Single<ForecastModel> cachedWeather = realmHelper.readForecast(latitude, longitude)
                        .onErrorReturnItem(new ForecastModel());

        return force ? networkWeather : Single.concat(cachedWeather, networkWeather)
                .filter(forecastModel -> forecastModel.getCurrentWeatherModels() != null).firstOrError();
    }

    @Override
    public Completable writeCurrentPlaceToFavorites() {
        String name = preferencesManager.getCurrentCityName();
        double latitude = preferencesManager.getCurrentLatitude();
        double longitude = preferencesManager.getCurrentLongitude();
        FavoritePlace favoritePlace = new FavoritePlace(name, latitude, longitude);
        return realmHelper.writeFavoritePlace(favoritePlace);
    }

    @Override
    public Completable deleteCurrentPlaceFromFavorites() {
        double latitude = preferencesManager.getCurrentLatitude();
        double longitude = preferencesManager.getCurrentLongitude();
        return realmHelper.removeFavoritePlace(latitude, longitude);
    }

    @Override
    public Single<Boolean> checkIsCurrentPlaceFavorite() {
        double latitude = preferencesManager.getCurrentLatitude();
        double longitude = preferencesManager.getCurrentLongitude();
        return realmHelper.checkCurrentPlaceInFavorites(latitude, longitude);
    }
}
