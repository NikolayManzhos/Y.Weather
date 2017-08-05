package com.example.weather.presentation.main;

import android.util.Log;

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

    @Override
    public void onAttach() {
        Log.i("main_pres", "onAttach: " + (getRouter() == null) + " " + (getView() == null));
    }

    @Override
    public void onDetach() {

    }
}
