package com.example.weather.presentation.main.home_screen.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TodayViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.todayContainer)
    public RelativeLayout todayContainer;

    @BindView(R.id.currentDate)
    public TextView currentDate;

    @BindView(R.id.temperatureDay)
    public TextView temperatureDay;

    @BindView(R.id.temperatureNight)
    public TextView temperatureNight;

    @BindView(R.id.icon)
    public ImageView icon;

    @BindView(R.id.condition)
    public TextView condition;

    public TodayViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
    }
}
