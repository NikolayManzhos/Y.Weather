package com.example.weather.presentation.main.detail_screen;

import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.common.BaseMainPresenter;

import javax.inject.Inject;

@PerFragment
public class DetailPresenter extends BaseMainPresenter<DetailView> {

    @Inject
    public DetailPresenter() {

    }
}
