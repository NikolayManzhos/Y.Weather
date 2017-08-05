package com.example.weather.utils.rx;


import io.reactivex.SingleTransformer;

public interface SchedulerProvider {
    <T>SingleTransformer<T, T> applyIoSchedulers();
}
