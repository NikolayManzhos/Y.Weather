package com.example.weather.domain;


import com.example.weather.TestSchedulerProvider;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.interactor.CurrentWeatherInteractorImpl;
import com.example.weather.domain.models.ForecastModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
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
        ForecastModel forecastModel = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(forecastModel));
        interactor.requestWeather(true)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(forecastModel);
    }

    @Test
    public void requestWeatherReset() {
        ForecastModel response = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response));
        interactor.requestWeather(true);

        ForecastModel response2 = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response2));
        interactor.requestWeather(true)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(response2);
    }

    private ForecastModel provideFilledModel() {
        return random(ForecastModel.class);
    }
}
