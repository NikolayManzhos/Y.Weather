package com.example.weather.presentation.main.home_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.example.weather.presentation.main.detail_screen.DetailFragment;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeFragment extends BaseMainFragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.forecast_recycler_view)
    RecyclerView forecastRecyclerView;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    HomePresenter homePresenter;

    @Inject
    HomeAdapter homeAdapter;

    private boolean twoPane;

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

        if (view.findViewById(R.id.details_container) != null) {
            twoPane = true;

            if (savedInstanceState == null) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.details_container, DetailFragment.newInstance())
                        .commit();
            }
        } else {
            twoPane = false;
        }
        initRecyclerView();
        if (savedInstanceState == null) {
            homePresenter.getCurrentWeather(false, true);
        } else {
            homePresenter.getCurrentWeather(false, false);
        }
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
    public void onRefresh() {
        homePresenter.getCurrentWeather(true, false);
    }

    @Override
    protected void inject() {
        ((MainActivity) getActivity()).getActivityComponent().plusFragmentComponent().inject(this);
    }

    private void initRecyclerView() {
        homeAdapter.setUseTodayLayout(!twoPane);
        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        forecastRecyclerView.setAdapter(homeAdapter);
    }
}
