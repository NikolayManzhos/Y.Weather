package com.example.weather.domain;

import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.domain.entities.DetailedWeather;
import com.example.weather.utils.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class GetCurrentWeatherInteractorImpl implements GetCurrentWeatherInteractor {

    private WeatherRepository weatherRepository;
    private SchedulerProvider schedulerProvider;

    private Disposable weatherDisposable;
    private ReplaySubject<DetailedWeather> weatherReplaySubject;

    public GetCurrentWeatherInteractorImpl(WeatherRepository weatherRepository,
                                           SchedulerProvider schedulerProvider) {
        this.weatherRepository = weatherRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<DetailedWeather> requestWeather(boolean force) {
        if (force && weatherDisposable != null) {
            weatherDisposable.dispose();
        }
        if (weatherDisposable == null || weatherDisposable.isDisposed()) {
            weatherReplaySubject = ReplaySubject.create(1);

            weatherDisposable = weatherRepository.getWeather(force)
                    .compose(schedulerProvider.applyIoSchedulers())
                    .subscribe(weatherReplaySubject::onNext, weatherReplaySubject::onError);
        }
        return weatherReplaySubject;
    }
}
