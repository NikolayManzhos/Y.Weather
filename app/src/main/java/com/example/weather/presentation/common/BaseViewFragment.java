package com.example.weather.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseViewFragment extends BaseFragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inject();
        getPresenter().setView(this);
        getPresenter().setRouter(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
        getPresenter().onAttach();
    }

    @Override
    public void onStop() {
        super.onStop();
        getPresenter().onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().setRouter(null);
        getPresenter().setView(null);
    }

    protected abstract BasePresenter getPresenter();
    protected abstract void inject();
}
