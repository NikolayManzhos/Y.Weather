package com.example.weather.presentation.di.component;

import com.example.weather.presentation.di.module.FragmentModule;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.detail_screen.DetailFragment;
import com.example.weather.presentation.main.home_screen.HomeFragment;
import com.example.weather.presentation.main.suggest_screen.SuggestFragment;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(HomeFragment homeFragment);
    void inject(SuggestFragment suggestFragment);
    void inject(DetailFragment detailFragment);
}
