package com.example.weather.presentation.main.common;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.weather.presentation.common.BaseViewFragment;

public abstract class BaseMainFragment extends BaseViewFragment implements BaseMainView {

    @Override
    public void hideLoad() {}

    @Override
    public void showLoad() {}

    @Override
    public void showError(@StringRes int message) {
        Toast.makeText(getContext(), getString(message), Toast.LENGTH_LONG).show();
    }
}
