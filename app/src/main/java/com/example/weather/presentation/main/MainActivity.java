package com.example.weather.presentation.main;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.example.weather.R;
import com.example.weather.WeatherApp;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.presentation.android_job.WeatherJob;
import com.example.weather.presentation.common.BaseActivity;
import com.example.weather.presentation.di.component.ActivityComponent;
import com.example.weather.presentation.di.component.DaggerActivityComponent;
import com.example.weather.presentation.di.module.ActivityModule;
import com.example.weather.presentation.main.aboutapp_screen.AboutAppFragment;
import com.example.weather.presentation.main.home_screen.HomeFragment;
import com.example.weather.presentation.main.settings_screen.SettingsFragment;
import com.example.weather.presentation.main.suggest_screen.SuggestFragment;
import com.example.weather.utils.OnCityChangeListener;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements MainRouter, MainView, NavigationView.OnNavigationItemSelectedListener, OnCityChangeListener, FragmentManager.OnBackStackChangedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    MainPresenter mainPresenter;

    @Inject
    PreferencesManager preferencesManager;

    private ActivityComponent activityComponent;

    private DrawerArrowDrawable homeDrawable;
    private boolean isHomeAsUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        homeDrawable = new DrawerArrowDrawable(toolbar.getContext());
        toolbar.setNavigationIcon(homeDrawable);

        toolbar.setNavigationOnClickListener(view -> {
            if (drawer.isDrawerOpen(GravityCompat.START)){
                drawer.closeDrawer(GravityCompat.START);
            } else if (isHomeAsUp){
                onBackPressed();
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            checkFirstTimeUser();
            getPresenter().selectedHome();
            navigationView.setCheckedItem(R.id.nav_home);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setHomeAsUp(true);
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

    @Override
    public void onBackStackChanged() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            setHomeAsUp(false);
        } else {
            setHomeAsUp(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                getPresenter().selectedHome();
                break;
            case R.id.nav_change_city:
                getPresenter().selectSuggestScreen();
                break;
            case R.id.nav_settings:
                getPresenter().selectedSettings();
                break;
            case R.id.nav_about_app:
                getPresenter().selectedAboutApp();
                break;
            default:
                getPresenter().selectedHome();
                break;
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
    public void showSuggestScreen() {
        replaceFragment(R.id.fl_main_frame, SuggestFragment.newInstance(), false);
    }

    @Override
    public void showSettingsScreen() {
        replaceFragment(R.id.fl_main_frame, SettingsFragment.newInstance(), true);
    }

    @Override
    public void showAboutApplicationScreen() {
        replaceFragment(R.id.fl_main_frame, AboutAppFragment.newInstance(), true);
    }

    @Override
    public void showError(@StringRes int message) {}

    @Override
    public void showLoad() {}

    @Override
    public void hideLoad() {}

    @Override
    public void cityChanged() {
        navigationView.setCheckedItem(R.id.nav_home);
        getPresenter().selectedHome();
    }

    @Override
    protected void inject() {
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((WeatherApp) getApplication()).getAppComponent())
                .build();
        activityComponent.inject(this);
    }

    @Override
    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    private void checkFirstTimeUser() {
        if (preferencesManager.getFirstTimeUser()) {
            long interval = Long.valueOf(preferencesManager.getCurrentUpdateInterval());
            WeatherJob.scheduleJob(interval);
            preferencesManager.setFirstTimeUser(false);
        }
    }

    private void setHomeAsUp(boolean isHomeAsUp) {
        if (this.isHomeAsUp != isHomeAsUp) {
            this.isHomeAsUp = isHomeAsUp;
            int lockMode = isHomeAsUp ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED;
            drawer.setDrawerLockMode(lockMode);
            ValueAnimator anim = isHomeAsUp ? ValueAnimator.ofFloat(0, 1) : ValueAnimator.ofFloat(1, 0);
            anim.addUpdateListener(valueAnimator -> {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                homeDrawable.setProgress(slideOffset);
            });
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(400);
            anim.start();
        }
    }
}
