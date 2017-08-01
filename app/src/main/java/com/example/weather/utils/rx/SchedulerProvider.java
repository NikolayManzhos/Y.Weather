package com.example.weather.utils.rx;


import io.reactivex.ObservableTransformer;

public interface SchedulerProvider {
    <T>ObservableTransformer<T, T> applyIoSchedulers();
}
