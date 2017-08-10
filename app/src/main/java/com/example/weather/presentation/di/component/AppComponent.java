package com.example.weather.presentation.di.component;

import com.example.weather.data.local.PreferencesManager;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.interactor.MainViewInteractor;
import com.example.weather.domain.interactor.SuggestViewInteractor;
import com.example.weather.presentation.android_job.WeatherJob;
import com.example.weather.presentation.di.module.AppModule;
import com.example.weather.presentation.di.module.DataModule;
import com.example.weather.presentation.di.module.DomainModule;
import com.example.weather.presentation.main.common.ViewModelMapper;
import com.example.weather.utils.rx.RxBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, DomainModule.class})
public interface AppComponent {
    PreferencesManager preferencesManager();
    CurrentWeatherInteractor currentWeatherInteractor();
    SuggestViewInteractor suggestViewInteractor();
    MainViewInteractor mainViewInteractor();
    ViewModelMapper viewModelMapper();
    RxBus rxBus();
    void inject(WeatherJob weatherJob);
}
