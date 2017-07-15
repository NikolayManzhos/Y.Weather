package com.example.weather.presentation.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        getPresenter().setView(this);
        getPresenter().setRouter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onAttach();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().setRouter(null);
    }

    protected abstract BasePresenter getPresenter();
    protected abstract void inject();
}
