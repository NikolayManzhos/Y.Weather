package com.example.weather.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.weather.presentation.di.HasComponentActivity;

public abstract class BaseActivity extends AppCompatActivity implements HasComponentActivity {

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
        getPresenter().setView(null);
    }

    protected void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            ft.replace(containerId, fragment).addToBackStack(null);
        } else {
            ft.replace(containerId, fragment);
        }
        ft.commit();
    }

    protected abstract BasePresenter getPresenter();
    protected abstract void inject();
}
