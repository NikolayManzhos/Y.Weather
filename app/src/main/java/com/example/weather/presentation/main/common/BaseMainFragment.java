package com.example.weather.presentation.main.common;

import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.presentation.common.BaseViewFragment;

public abstract class BaseMainFragment extends BaseViewFragment implements BaseMainView {

    @Override
    public void hideLoad() {}

    @Override
    public void showLoad() {}

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
    }
}
