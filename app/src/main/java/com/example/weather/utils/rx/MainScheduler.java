package com.example.weather.utils.rx;


import io.reactivex.Scheduler;

public interface MainScheduler {
    Scheduler applyMainThread();
}
