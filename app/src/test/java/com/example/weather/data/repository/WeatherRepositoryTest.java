package com.example.weather.data.repository;

import com.example.weather.data.local.CacheManager;
import com.example.weather.data.WeatherApi;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.data.repository.weather.WeatherRepositoryImpl;
import com.example.weather.domain.entities.DetailedWeather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class WeatherRepositoryTest {

    @Mock
    private WeatherApi api;

    @Mock
    CacheManager cacheManager;

    @Mock
    PreferencesManager preferencesManager;

    private WeatherRepository weatherRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        weatherRepository = new WeatherRepositoryImpl(api, cacheManager, preferencesManager);
        when(preferencesManager.getLatitude()).thenReturn(55.5F);
        when(preferencesManager.getLongitude()).thenReturn(34.4F);
    }

    @Test
    public void getWeatherForce() {
        DetailedWeather networkResponse = new DetailedWeather();
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Observable.just(networkResponse));

        weatherRepository.getWeather(true)
                .test()
                .assertNoErrors()
                .assertValue(networkResponse);
    }

    @Test
    public void getWeatherLocalSuccess() {
        DetailedWeather localResponse = provideRandomResponse();
        when(cacheManager.getLastWeather()).thenReturn(localResponse);
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Observable.empty());

        weatherRepository.getWeather(false)
                .test()
                .assertNoErrors()
                .assertValue(localResponse);
    }

    @Test
    public void getWeatherLocalFailure() {
        Exception networkException = new Exception("Network exception");
        when(cacheManager.getLastWeather()).thenReturn(new DetailedWeather());
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Observable.error(networkException));

        weatherRepository.getWeather(false)
                .test()
                .assertNoValues()
                .assertError(networkException);
    }

    @Test
    public void getWeatherLocalRedirectToNetwork() {
        DetailedWeather networkResponse = provideRandomResponse();
        when(cacheManager.getLastWeather()).thenReturn(new DetailedWeather());
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Observable.just(networkResponse));

        weatherRepository.getWeather(false)
                .test()
                .assertNoErrors()
                .assertValue(networkResponse);
    }

    private DetailedWeather provideRandomResponse() {
        return random(DetailedWeather.class);
    }
}
