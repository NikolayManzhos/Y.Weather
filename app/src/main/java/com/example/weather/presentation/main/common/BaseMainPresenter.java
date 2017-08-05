package com.example.weather.presentation.main.common;

import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainRouter;

public abstract class BaseMainPresenter<View extends BaseMainView>
        extends BasePresenter<View, MainRouter> {

    @Override
    public void onAttach() {}

    @Override
    public void onDetach() {}
}
