package com.example.weather.presentation.main;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.example.weather.R;
import com.example.weather.WeatherApp;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.presentation.android_job.WeatherJob;
import com.example.weather.presentation.common.BaseActivity;
import com.example.weather.presentation.di.component.ActivityComponent;
import com.example.weather.presentation.di.component.DaggerActivityComponent;
import com.example.weather.presentation.di.module.ActivityModule;
import com.example.weather.presentation.main.aboutapp_screen.AboutAppFragment;
import com.example.weather.presentation.main.detail_screen.DetailFragment;
import com.example.weather.presentation.main.home_screen.HomeFragment;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;
import com.example.weather.presentation.main.settings_screen.SettingsFragment;
import com.example.weather.presentation.main.suggest_screen.SuggestFragment;
import com.example.weather.utils.OnCityChangeListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements MainRouter, MainView, NavigationView.OnNavigationItemSelectedListener,
        OnCityChangeListener, FragmentManager.OnBackStackChangedListener, FavoritesAdapter.OnFavoriteClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Inject
    MainPresenter mainPresenter;

    @Inject
    FavoritesAdapter adapter;

    @Inject
    PreferencesManager preferencesManager;

    private ActivityComponent activityComponent;

    private DrawerArrowDrawable homeDrawable;
    private RecyclerView favoritesRecyclerView;
    private boolean isHomeAsUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initFavoriteRecyclerView();

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
        favoritesRecyclerView = navigationView.findViewById(R.id.favoritesRecyclerView);

        if (savedInstanceState == null) {
            checkFirstTimeUser();
            getPresenter().selectedHome();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            setHomeAsUp(true);
        }
        mainPresenter.requestFavoriteItems();
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

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showHomeScreen() {
        replaceFragment(R.id.fl_main_frame, HomeFragment.newInstance(), false);
    }

    @Override
    public void showSuggestScreen() {
        replaceFragment(R.id.fl_main_frame, SuggestFragment.newInstance(), true);
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
    public void showDetailsScreen(WeatherViewModel weatherViewModel) {
        replaceFragment(R.id.fl_main_frame, DetailFragment.newInstance(weatherViewModel), true);
    }

    @Override
    public void showError() {}

    @Override
    public void showLoad() {}

    @Override
    public void hideLoad() {}

    @Override
    public void displayFavoriteItems(List<FavoritePlace> favoritePlaces) {
        Log.d("MainView", String.valueOf(favoritePlaces.size()));
        adapter.setData(favoritePlaces);
    }

    @Override
    public void cityChanged() {
        onBackPressed();
    }

    @Override
    public void showFavRemoveError() {

    }

    @Override
    public void onFavoriteClick(FavoritePlace favoritePlace) {
        drawer.closeDrawer(GravityCompat.START);
        mainPresenter.changeCurrentPlace(favoritePlace);
    }

    @Override
    public void onFavoriteRemoveClick(int position, FavoritePlace favoritePlace) {
        mainPresenter.removePlaceFromFavorites(favoritePlace, position);
    }

    @Override
    public void confirmFavoriteRemoved(int position) {
        adapter.removeItem(position);
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

    private void initFavoriteRecyclerView() {
        favoritesRecyclerView = navigationView.getHeaderView(0).findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        favoritesRecyclerView.setAdapter(adapter);
        adapter.setOnFavoriteClickListener(this);
    }
}
