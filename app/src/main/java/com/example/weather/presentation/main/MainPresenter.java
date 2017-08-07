package com.example.weather.presentation.main;

import com.example.weather.presentation.di.scope.PerActivity;
import com.example.weather.presentation.main.common.BaseMainPresenter;

import javax.inject.Inject;

@PerActivity
public class MainPresenter extends BaseMainPresenter<MainView> {

    @Inject
    public MainPresenter() {}

    public void selectedHome() {
        getRouter().showHomeScreen();
    }

    public void selectSuggestScreen() {
        getRouter().showSuggestScreen();
    }

    public void selectedSettings() {
        getRouter().showSettingsScreen();
    }

    public void selectedAboutApp() {
        getRouter().showAboutApplicationScreen();
    }
}
