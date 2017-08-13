package com.example.weather;

import android.annotation.SuppressLint;
import android.app.Application;

import com.evernote.android.job.JobManager;
import com.example.weather.presentation.android_job.WeatherJobCreator;
import com.example.weather.presentation.di.component.AppComponent;
import com.example.weather.presentation.di.component.DaggerAppComponent;
import com.example.weather.presentation.di.module.AppModule;

import io.realm.Realm;

@SuppressLint("Registered")
public class WeatherApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        JobManager.create(this).addJobCreator(new WeatherJobCreator());
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
