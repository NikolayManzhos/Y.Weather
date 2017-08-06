package com.example.weather.domain;

import com.example.weather.data.entities.weather.ForecastWeather;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.domain.models.CurrentWeatherModel;
import com.example.weather.domain.models.ForecastModel;
import com.example.weather.utils.ConvertUtils;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.RealmList;

@Singleton
public class ModelMapper {

    private PreferencesManager preferencesManager;
    private ConvertUtils utils;

    @Inject
    ModelMapper(PreferencesManager preferencesManager,
                       ConvertUtils utils) {
        this.preferencesManager = preferencesManager;
        this.utils = utils;
    }

    public ForecastModel entityToModel(ForecastWeather forecastWeather) {
        RealmList<CurrentWeatherModel> weatherList = new RealmList<>();
        Observable.fromIterable(forecastWeather.getList())
                .forEach(list -> weatherList.add(new CurrentWeatherModel(
                        list.getWeather().get(0).getMain(),
                        list.getDt(),
                        list.getWeather().get(0).getId(),
                        utils.convertTemperature(list.getTemp().getDay()),
                        utils.convertTemperature(list.getTemp().getNight()),
                        utils.convertWindSpeed(list.getSpeed()),
                        list.getHumidity(),
                        utils.convertPressure(list.getPressure())
                )));
        return new ForecastModel(preferencesManager.getCurrentCityName(),
                weatherList,
                preferencesManager.getCurrentLatitude(),
                preferencesManager.getCurrentLongitude());
    }

}
