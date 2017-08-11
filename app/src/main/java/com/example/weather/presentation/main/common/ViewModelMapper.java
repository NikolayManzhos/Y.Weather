package com.example.weather.presentation.main.common;

import android.util.Log;

import com.example.weather.domain.models.CurrentWeatherModel;
import com.example.weather.domain.models.ForecastModel;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;
import com.example.weather.utils.ConvertUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
                .filter(currentWeatherModel -> {
                    Calendar date = Calendar.getInstance(Locale.getDefault());
                    date.set(Calendar.HOUR_OF_DAY, 0);
                    date.set(Calendar.MINUTE, 0);
                    date.set(Calendar.SECOND, 0);
                    date.set(Calendar.MILLISECOND, 0);
                    return ((long) currentWeatherModel.getDate()) * 1000 > date.getTimeInMillis();
                })
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
