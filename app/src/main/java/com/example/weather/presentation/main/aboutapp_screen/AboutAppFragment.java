package com.example.weather.presentation.main.aboutapp_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.R;
import com.example.weather.WeatherApp;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.common.BaseMainFragment;

import javax.inject.Inject;


public class AboutAppFragment extends BaseMainFragment implements AboutAppView {

    @Inject
    AboutAppPresenter aboutAppPresenter;

    public static AboutAppFragment newInstance() {
        return new AboutAppFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_about_app;
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return aboutAppPresenter;
    }

    @Override
    protected void inject() {
        WeatherApp.getInstance().plusAboutAppComponent().inject(this);
    }
}
