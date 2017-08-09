package com.example.weather.presentation.main.home_screen.vh;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FutureViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.futureContainer)
    public RelativeLayout futureContainer;

    @BindView(R.id.icon)
    public ImageView icon;

    @BindView(R.id.date)
    public TextView date;

    @BindView(R.id.condition)
    public TextView condition;

    @BindView(R.id.temperatureDay)
    public TextView temperatureDay;

    @BindView(R.id.temperatureNight)
    public TextView temperatureNight;

    public FutureViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
