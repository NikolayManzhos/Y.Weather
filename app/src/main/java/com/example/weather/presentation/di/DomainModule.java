package com.example.weather.presentation.di;

import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.domain.CurrentWeatherInteractor;
import com.example.weather.domain.CurrentWeatherInteractorImpl;
import com.example.weather.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @Provides
    @Singleton
    CurrentWeatherInteractor provideGetCurrentWeatherInteractor(WeatherRepository weatherRepository,
                                                                SchedulerProvider schedulerProvider) {
        return new CurrentWeatherInteractorImpl(weatherRepository, schedulerProvider);
    }
}
