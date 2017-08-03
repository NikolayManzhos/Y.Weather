package com.example.weather.presentation.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.weather.presentation.di.ActivityContext;
import com.example.weather.presentation.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @ActivityContext
    @PerActivity
    @Provides
    Context provideContext() {
        return activity;
    }
}
