package com.example.weather.presentation.di.component;

import com.example.weather.presentation.di.module.FragmentModule;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.home_screen.HomeFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomeFragment homeFragment);
}
