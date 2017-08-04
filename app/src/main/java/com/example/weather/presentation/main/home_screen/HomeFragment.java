package com.example.weather.presentation.main.home_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.MainActivity;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class HomeFragment extends BaseMainFragment implements HomeView, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "tag_home_fragment";

    @BindView(R.id.tv_temperature)
    TextView tvTemperature;

    @BindView(R.id.tv_city)
    TextView tvCity;

    @BindView(R.id.tv_weather)
    TextView tvWeather;

    @BindView(R.id.tv_wind)
    TextView tvWind;

    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    HomePresenter homePresenter;

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
        tvCity.setText(homeViewModel.getCity());
        tvTemperature.setText(homeViewModel.getTemperature());
        tvWeather.setText(homeViewModel.getMain());
        tvWind.setText(homeViewModel.getWind());
        Picasso.with(getContext()).load(homeViewModel.getIconId()).into(ivIcon);
        Log.i(TAG, "showWeather: " + homeViewModel.toString());
    }

    @Override
    public void onRefresh() {
        homePresenter.getCurrentWeather(true);
    }

    @Override
    protected void inject() {
        ((MainActivity) getActivity()).getActivityComponent().plusFragmentComponent().inject(this);
    }
}
