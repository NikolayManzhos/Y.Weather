package com.example.weather.utils.rx;


import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AppMainScheduler implements MainScheduler {

    @Override
    public Scheduler applyMainThread() {
        return AndroidSchedulers.mainThread();
    }
}
