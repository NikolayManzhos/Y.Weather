package com.example.weather;

import android.app.Application;

import com.evernote.android.job.JobManager;
import com.example.weather.presentation.android_job.WeatherJobCreator;
import com.example.weather.presentation.di.component.AppComponent;
import com.example.weather.presentation.di.component.DaggerAppComponent;
import com.example.weather.presentation.di.module.AppModule;

public class WeatherApp extends Application {
    public static final String PREF_NAME = "weather_prefs";

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        JobManager.create(this).addJobCreator(new WeatherJobCreator());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
