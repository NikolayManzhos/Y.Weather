package com.example.weather.presentation.di;

import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.domain.GetCurrentWeatherInteractor;
import com.example.weather.domain.GetCurrentWeatherInteractorImpl;
import com.example.weather.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @Provides
    @Singleton
    GetCurrentWeatherInteractor provideGetCurrentWeatherInteractor(WeatherRepository weatherRepository,
                                                                   SchedulerProvider schedulerProvider) {
        return new GetCurrentWeatherInteractorImpl(weatherRepository, schedulerProvider);
    }
}
