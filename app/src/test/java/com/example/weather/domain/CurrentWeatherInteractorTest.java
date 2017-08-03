package com.example.weather.domain;


import com.example.weather.TestSchedulerProvider;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.domain.entities.weather.DetailedWeather;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.interactor.CurrentWeatherInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.when;

public class CurrentWeatherInteractorTest {

    @Mock
    private WeatherRepository weatherRepository;

    private CurrentWeatherInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new CurrentWeatherInteractorImpl(weatherRepository, new TestSchedulerProvider());
    }

    @Test
    public void requestWeatherSuccess() {
        DetailedWeather response = new DetailedWeather();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response));
        interactor.requestWeather(true)
                .test()
                .assertNoErrors()
                .assertValue(response);
    }

    @Test
    public void requestWeatherReset() {
        DetailedWeather response = new DetailedWeather();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response));
        interactor.requestWeather(true);

        DetailedWeather response2 = new DetailedWeather();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response2));
        interactor.requestWeather(true)
                .test()
                .assertNoErrors()
                .assertValue(response2);

    }
}
