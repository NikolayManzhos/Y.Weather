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

import io.reactivex.Completable;
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
        interactor.requestWeather(true, false)
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
        interactor.requestWeather(true, false);

        ForecastModel response2 = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response2));
        interactor.requestWeather(true, false)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(response2);
    }

    @Test
    public void requestWeatherTryAgainOnError() {
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.error(new Exception()));
        interactor.requestWeather(true, false);

        ForecastModel response2 = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response2));
        interactor.requestWeather(true, false)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(response2);
    }

    @Test
    public void requestWeatherCheckCache() {
        ForecastModel response = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response));
        interactor.requestWeather(true, false);

        ForecastModel response2 = provideFilledModel();
        when(weatherRepository.getWeather(anyBoolean()))
                .thenReturn(Single.just(response2));
        interactor.requestWeather(false, true)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValues(response2);
    }

    @Test
    public void addToFavoritesSuccess() {
        when(weatherRepository.writeCurrentPlaceToFavorites()).thenReturn(Completable.complete());

        interactor.addToFavorites()
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void removeFromFavoritesSuccess() {
        when(weatherRepository.deleteCurrentPlaceFromFavorites()).thenReturn(Completable.complete());

        interactor.removeFromFavorites()
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void checkCurrentPlaceInFavoritesSuccess() {
        when(weatherRepository.checkIsCurrentPlaceFavorite()).thenReturn(Single.just(true));

        interactor.checkCurrentPlaceInFavorites()
                .test()
                .assertNoErrors()
                .assertValue(true);
    }

    private ForecastModel provideFilledModel() {
        return random(ForecastModel.class);
    }
}
