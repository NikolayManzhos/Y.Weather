package com.example.weather.presentation.di.module;

import com.example.weather.data.repository.suggest.SuggestRepository;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.interactor.CurrentWeatherInteractorImpl;
import com.example.weather.domain.interactor.SuggestViewInteractor;
import com.example.weather.domain.interactor.SuggestViewInteractorImpl;
import com.example.weather.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @Provides
    @Singleton
    CurrentWeatherInteractor provideCurrentWeatherInteractor(WeatherRepository weatherRepository,
                                                                SchedulerProvider schedulerProvider) {
        return new CurrentWeatherInteractorImpl(weatherRepository, schedulerProvider);
    }

    @Provides
    @Singleton
    SuggestViewInteractor providesSuggestViewInteractor(SuggestRepository suggestRepository,
                                                        SchedulerProvider schedulerProvider) {
        return new SuggestViewInteractorImpl(suggestRepository, schedulerProvider);
    }
}
