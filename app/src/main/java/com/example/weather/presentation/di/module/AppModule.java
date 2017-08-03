package com.example.weather.presentation.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.weather.data.local.PreferencesManager;
import com.example.weather.presentation.di.ApplicationContext;
import com.example.weather.utils.rx.AppSchedulerProvider;
import com.example.weather.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(@NonNull Application application) {
        this.application = application;
    }

    @ApplicationContext
    @Singleton
    @Provides
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    PreferencesManager providePreferenceManager(SharedPreferences sharedPreferences) {
        return new PreferencesManager(sharedPreferences);
    }

    @Singleton
    @Provides
    SchedulerProvider provideScedulers() {
        return new AppSchedulerProvider();
    }
}
