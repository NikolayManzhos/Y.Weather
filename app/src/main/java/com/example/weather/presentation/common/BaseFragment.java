package com.example.weather.presentation.common;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @LayoutRes
    protected abstract int provideLayout();

    @Nullable
    protected String provideToolbarTitle() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(provideLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (provideToolbarTitle() != null) getActivity().setTitle(provideToolbarTitle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
