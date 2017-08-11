package com.example.weather.presentation.main.common;

import com.example.weather.domain.models.CurrentWeatherModel;
import com.example.weather.domain.models.ForecastModel;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;
import com.example.weather.utils.ConvertUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.realm.RealmList;

@Singleton
public class ViewModelMapper {

    private ConvertUtils utils;

    @Inject
    ViewModelMapper(ConvertUtils utils) {
        this.utils = utils;
    }

    public HomeViewModel forecastModelToViewModel(ForecastModel forecastModel) {
        return new HomeViewModel(provideWeatherList(forecastModel.getCurrentWeatherModels()),
                forecastModel.getCityName());
    }

    private List<WeatherViewModel> provideWeatherList(RealmList<CurrentWeatherModel> weatherModels) {
        return Observable
                .fromIterable(weatherModels)
                .filter(currentWeatherModel -> (long) currentWeatherModel.getDate() * 1000 > System.currentTimeMillis())
                .map(currentWeatherModel -> new WeatherViewModel(
                        currentWeatherModel.getCondition(),
                        utils.convertTime((long)currentWeatherModel.getDate() * 1000),
                        utils.convertId(currentWeatherModel.getIconId()),
                        utils.convertTemperature(currentWeatherModel.getTemperature()),
                        utils.convertTemperature(currentWeatherModel.getTemperatureNight()),
                        utils.convertWindSpeed(currentWeatherModel.getWindSpeed()),
                        utils.convertHumidity(currentWeatherModel.getHumidity()),
                        utils.convertPressure(currentWeatherModel.getPressure()))).toList().blockingGet();
    }
}
