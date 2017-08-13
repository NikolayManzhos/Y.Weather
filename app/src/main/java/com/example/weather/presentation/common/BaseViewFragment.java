package com.example.weather.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public abstract class BaseViewFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inject();
        getPresenter().setView(this);
        getPresenter().setRouter(getActivity());
        getPresenter().onAttach();
    }

    @Override
    public void onDestroyView() {
        getPresenter().onDetach();
        getPresenter().setRouter(null);
        getPresenter().setView(null);
        super.onDestroyView();
    }

    protected abstract BasePresenter getPresenter();
    protected abstract void inject();
}
