package com.example.weather.presentation.main.aboutapp_screen;


import android.support.annotation.Nullable;

import com.example.weather.R;
import com.example.weather.presentation.common.BaseFragment;


public class AboutAppFragment extends BaseFragment implements AboutAppView {

    public static AboutAppFragment newInstance() {
        return new AboutAppFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_about_app;
    }

    @Nullable
    @Override
    protected String provideToolbarTitle() {
        return getString(R.string.about_toolbar_title);
    }

    @Override
    public void showLoad() {}

    @Override
    public void hideLoad() {}

    @Override
    public void showError(int message) {}
}
