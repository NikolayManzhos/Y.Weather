package com.example.weather.presentation.main.home_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.WeatherApp;
import com.example.weather.presentation.common.BasePresenter;
import com.example.weather.presentation.main.common.BaseMainFragment;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
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
    public String getTitle() {
        return getString(R.string.app_name);
    }

    @Override
    protected BasePresenter getPresenter() {
        return homePresenter;
    }

    @Override
    protected void inject() {
        WeatherApp.getInstance().plusHomeComponent().inject(this);
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        ((HomePresenter) getPresenter()).refreshweather();
    }
}
