package com.example.weather.domain;

import com.example.weather.data.entities.weather.DetailedWeather;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.domain.models.CurrentWeather;
import com.example.weather.utils.ConvertUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ModelMapper {

    private PreferencesManager preferencesManager;
    private ConvertUtils utils;

    @Inject
    public ModelMapper(PreferencesManager preferencesManager,
                       ConvertUtils utils) {
        this.preferencesManager = preferencesManager;
        this.utils = utils;
    }

    public CurrentWeather entityToModel(DetailedWeather detailedWeather) {
        return new CurrentWeather(
                preferencesManager.getCurrentCityName(),
                detailedWeather.getWeather().get(0).getMain(),
                utils.convertTemperature(detailedWeather.getMain().getTemp()),
                utils.convertWindSpeed(detailedWeather.getWind().getSpeed()),
                detailedWeather.getMain().getHumidity());
    }

}
