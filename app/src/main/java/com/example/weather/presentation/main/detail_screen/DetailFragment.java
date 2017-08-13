package com.example.weather.presentation.main.detail_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.presentation.common.BaseFragment;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;
import com.example.weather.utils.GlobalConstants;

import butterknife.BindView;


public class DetailFragment extends BaseFragment {

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.condition)
    TextView condition;

    @BindView(R.id.currentDate)
    TextView currentDate;

    @BindView(R.id.temperatureDay)
    TextView temperatureDay;

    @BindView(R.id.temperatureNight)
    TextView temperatureNight;

    @BindView(R.id.humidity)
    TextView humidity;

    @BindView(R.id.pressure)
    TextView pressure;

    @BindView(R.id.windSpeed)
    TextView windSpeed;

    public static DetailFragment newInstance(WeatherViewModel weatherViewModel) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(GlobalConstants.EXTRAS_DETAILED_WEATHER, weatherViewModel);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WeatherViewModel weatherViewModel = getArguments().getParcelable(GlobalConstants.EXTRAS_DETAILED_WEATHER);
        if (weatherViewModel != null) {
            icon.setImageResource(weatherViewModel.getIconId());
            currentDate.setText(weatherViewModel.getDate());
            condition.setText(weatherViewModel.getCondition());
            temperatureDay.setText(weatherViewModel.getTemperature());
            temperatureNight.setText(weatherViewModel.getTemperatureNight());
            humidity.setText(weatherViewModel.getHumidity());
            pressure.setText(weatherViewModel.getPressure());
            windSpeed.setText(weatherViewModel.getWindSpeed());
        }
    }
}
