package com.example.weather.presentation.main.home_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.example.weather.presentation.main.detail_screen.DetailFragment;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseMainFragment
        implements HomeView, SwipeRefreshLayout.OnRefreshListener, HomeAdapter.OnDayClickListener {

    @BindView(R.id.forecast_recycler_view)
    RecyclerView forecastRecyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.favoriteFab)
    FloatingActionButton favorite;

    @Inject
    HomePresenter homePresenter;

    @Inject
    HomeAdapter homeAdapter;

    private boolean twoPane;
    private boolean backFromDetails = false;
    private boolean isFavorite = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_home;
    }

    @Nullable
    @Override
    protected String provideToolbarTitle() {
        return getString(R.string.home_toolbar_title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);

        twoPane = view.findViewById(R.id.details_container) != null;
        initRecyclerView();
        if (savedInstanceState == null && !backFromDetails) {
            homePresenter.getCurrentWeather(false, true);
        } else {
            homePresenter.getCurrentWeather(false, false);
            backFromDetails = false;
        }
        homePresenter.checkCurrentPlaceFavoriteStatus();
    }

    @Override
    public void showLoad() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoad() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected BasePresenter getPresenter() {
        return homePresenter;
    }

    @Override
    public void showWeather(HomeViewModel homeViewModel) {
        getActivity().setTitle(homeViewModel.getCityName());
        homeAdapter.setData(homeViewModel.getForecast());
    }

    @Override
    public void showRemoveError() {
        Toast.makeText(getContext().getApplicationContext(), R.string.home_remove_from_fav_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFavoriteStatus(boolean isFavorite) {
        this.isFavorite = isFavorite;
        favorite.setImageResource(isFavorite ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);
    }

    @Override
    public void onRefresh() {
        homePresenter.getCurrentWeather(true, false);
    }

    @OnClick(R.id.favoriteFab)
    void onFavoriteClick() {
        if (isFavorite) {
            homePresenter.removeCurrentPlaceFromFavorites();
        } else {
            homePresenter.addCurrentPlaceToFavorites();
        }
    }

    @Override
    public void onDayClick(WeatherViewModel weatherViewModel) {
        if (twoPane) {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.details_container, DetailFragment.newInstance(weatherViewModel))
                    .commit();
        } else {
            backFromDetails = true;
            homePresenter.showDetailsScreen(weatherViewModel);
        }
    }

    @Override
    protected void inject() {
        ((MainActivity) getActivity()).getActivityComponent().plusFragmentComponent().inject(this);
    }

    private void initRecyclerView() {
        homeAdapter.setUseTodayLayout(!twoPane);
        homeAdapter.setOnDayClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        forecastRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation()));
        forecastRecyclerView.setAdapter(homeAdapter);
    }
}
