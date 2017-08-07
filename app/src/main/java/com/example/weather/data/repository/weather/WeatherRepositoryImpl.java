package com.example.weather.data.repository.weather;

import com.example.weather.BuildConfig;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.network.WeatherApi;
import com.example.weather.domain.ModelMapper;
import com.example.weather.domain.models.ForecastModel;

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

}
