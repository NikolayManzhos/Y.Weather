package com.example.weather.presentation.di.component;

import com.example.weather.presentation.di.module.ActivityModule;
import com.example.weather.presentation.di.scope.PerActivity;
import com.example.weather.presentation.main.MainActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    FragmentComponent plusFragmentComponent();
}
