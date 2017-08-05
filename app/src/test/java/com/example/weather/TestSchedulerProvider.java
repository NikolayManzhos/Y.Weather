package com.example.weather;

import com.example.weather.utils.rx.SchedulerProvider;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.schedulers.Schedulers;


public class TestSchedulerProvider implements SchedulerProvider {

    @Override
    public <T> SingleTransformer<T, T> applyIoSchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }

    @Override
    public CompletableTransformer applyIoSchedulersCompletable() {
        return completable -> completable
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }
}
