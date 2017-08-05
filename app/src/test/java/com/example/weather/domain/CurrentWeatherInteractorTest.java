package com.example.weather.domain;


import com.example.weather.TestSchedulerProvider;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.data.entities.weather.DetailedWeather;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.interactor.CurrentWeatherInteractorImpl;
import com.example.weather.domain.models.CurrentWeather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.when;

public class CurrentWeatherInteractorTest {

    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private ModelMapper mapper;

    private CurrentWeatherInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new CurrentWeatherInteractorImpl(weatherRepository,
                mapper,
                new TestSchedulerProvider());
    }

    @Test
    public void requestWeatherSuccess() {
        DetailedWeather response = provideFilledEntity();
        CurrentWeather model = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response));
        when(mapper.entityToModel(any(DetailedWeather.class))).thenReturn(model);
        interactor.requestWeather(true)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(model);
    }

    @Test
    public void requestWeatherReset() {
        DetailedWeather response = provideFilledEntity();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response));
        interactor.requestWeather(true);

        DetailedWeather response2 = new DetailedWeather();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response2));
        CurrentWeather model = provideFilledModel();
        when(mapper.entityToModel(response2)).thenReturn(model);
        interactor.requestWeather(true)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(model);
    }

    private DetailedWeather provideFilledEntity() {
        return random(DetailedWeather.class);
    }

    private CurrentWeather provideFilledModel() {
        return random(CurrentWeather.class);
    }
}
