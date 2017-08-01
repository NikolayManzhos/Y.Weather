package com.example.weather.presentation.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.weather.data.local.PreferencesManager;
import com.example.weather.utils.rx.AppSchedulerProvider;
import com.example.weather.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context context;

    public AppModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public PreferencesManager providePreferenceManager(SharedPreferences sharedPreferences) {
        return new PreferencesManager(sharedPreferences);
    }

    @Singleton
    @Provides
    SchedulerProvider provideScedulers() {
        return new AppSchedulerProvider();
    }
}
