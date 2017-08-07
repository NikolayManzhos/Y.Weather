package com.example.weather.presentation.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
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

    @Singleton
    @ApplicationContext
    @Provides
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return application.getResources();
    }

    @Singleton
    @Provides
    PreferencesManager providePreferenceManager(SharedPreferences sharedPreferences) {
        return new PreferencesManager(sharedPreferences);
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulers() {
        return new AppSchedulerProvider();
    }
}
