package com.example.weather;

import com.example.weather.utils.rx.SchedulerProvider;

import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;


public class TestSchedulerProvider implements SchedulerProvider {

    @Override
    public <T> ObservableTransformer<T, T> applyIoSchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }
}
