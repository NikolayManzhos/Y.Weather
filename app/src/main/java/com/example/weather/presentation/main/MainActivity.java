package com.example.weather.presentation.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.weather.R;
import com.example.weather.WeatherApp;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.presentation.android_job.WeatherJob;
import com.example.weather.presentation.common.BaseActivity;
import com.example.weather.presentation.main.aboutapp_screen.AboutAppFragment;
import com.example.weather.presentation.main.home_screen.HomeFragment;
import com.example.weather.presentation.main.settings_screen.SettingsFragment;
import com.example.weather.utils.OnCityChangeListener;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

public class MainActivity extends BaseActivity
        implements MainRouter, MainView, NavigationView.OnNavigationItemSelectedListener, OnCityChangeListener {

    @Inject
    MainPresenter mainPresenter;

    @Inject
    PreferencesManager preferencesManager;

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            checkFirstTimeUser();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_main_frame, HomeFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public MainPresenter getPresenter() {
        return mainPresenter;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            getPresenter().selectedSettings();
        } else if (id == R.id.nav_about_app) {
            getPresenter().selectedAboutApp();
        } else if (id == R.id.nav_home) {
            getPresenter().selectedHome();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showHomeScreen() {
        replaceFragment(R.id.fl_main_frame, HomeFragment.newInstance(), false);
    }

    @Override
    public void showSettingsScreen() {
        replaceFragment(R.id.fl_main_frame, SettingsFragment.newInstance(), false);
    }

    @Override
    public void showAboutApplicationScreen() {
        replaceFragment(R.id.fl_main_frame, AboutAppFragment.newInstance(), false);
    }

    @Override
    public void showError(@StringRes int message) {

    }

    @Override
    public void showLoad() {}

    @Override
    public void hideLoad() {}

    @Override
    protected void inject() {
        WeatherApp.getInstance().plusMainActivityComponent().inject(this);
    }

    @Override
    public void cityChanged(LatLng latLng) {
        preferencesManager.setLatitude(latLng.latitude);
        preferencesManager.setLongitude(latLng.longitude);
        showHomeScreen();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void checkFirstTimeUser() {
        if (preferencesManager.getFirstTimeUser()) {
            long interval = Long.valueOf(preferencesManager.getCurrentUpdateInterval());
            WeatherJob.scheduleJob(interval);
            preferencesManager.setFirstTimeUser(false);
        }
    }
}
