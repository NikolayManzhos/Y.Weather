package com.example.weather.data.repository;

import com.example.weather.data.entities.weather.ForecastWeather;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.network.WeatherApi;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.data.repository.weather.WeatherRepositoryImpl;
import com.example.weather.domain.ModelMapper;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.domain.models.ForecastModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Single;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class WeatherRepositoryTest {

    @Mock
    private WeatherApi api;

    @Mock
    private PreferencesManager preferencesManager;

    @Mock
    private RealmHelper realmHelper;

    @Mock
    private ModelMapper mapper;

    private WeatherRepository weatherRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        weatherRepository = new WeatherRepositoryImpl(api, preferencesManager, realmHelper, mapper);
        when(preferencesManager.getCurrentLatitude()).thenReturn(55.5F);
        when(preferencesManager.getCurrentLongitude()).thenReturn(34.4F);
    }

    @Test
    public void getWeatherForce() {
        ForecastWeather forecastWeather = provideRandomResponse();
        ForecastModel forecastModel = provideRandomModel();
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Single.just(forecastWeather));
        when(realmHelper.readForecast(anyDouble(), anyDouble())).thenReturn(Single.just(new ForecastModel()));
        when(mapper.entityToModel(forecastWeather)).thenReturn(forecastModel);

        weatherRepository.getWeather(true)
                .test()
                .assertNoErrors()
                .assertValue(forecastModel);
    }

    @Test
    public void getWeatherLocalSuccess() {
        ForecastModel forecastModel = provideRandomModel();
        when(realmHelper.readForecast(anyDouble(), anyDouble())).thenReturn(Single.just(forecastModel));
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Single.never());

        weatherRepository.getWeather(false)
                .test()
                .assertNoErrors()
                .assertValue(forecastModel);
    }

    @Test
    public void getWeatherLocalFailure() {
        Exception networkException = new Exception("Network exception");
        when(realmHelper.readForecast(anyDouble(), anyDouble())).thenReturn(Single.error(new Exception()));
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Single.error(networkException));

        weatherRepository.getWeather(false)
                .test()
                .assertNoValues()
                .assertError(networkException);
    }

    @Test
    public void getWeatherLocalRedirectToNetwork() {
        ForecastWeather networkResponse = provideRandomResponse();
        ForecastModel convertedResponse = provideRandomModel();
        when(realmHelper.readForecast(anyDouble(), anyDouble())).thenReturn(Single.error(new Exception()));
        when(api.getCurrentWeather(anyFloat(), anyFloat(), anyString())).thenReturn(Single.just(networkResponse));
        when(mapper.entityToModel(any(ForecastWeather.class))).thenReturn(convertedResponse);

        weatherRepository.getWeather(false)
                .test()
                .assertNoErrors()
                .assertValue(convertedResponse);
    }

    @Test
    public void writePlaceToFavoriteCall() {
        when(preferencesManager.getCurrentCityName()).thenReturn("Moscow");
        when(preferencesManager.getCurrentLatitude()).thenReturn(55.5f);
        when(preferencesManager.getCurrentLatitude()).thenReturn(35.4f);
        when(realmHelper.writeFavoritePlace(any(FavoritePlace.class))).thenReturn(Completable.complete());

        weatherRepository.writeCurrentPlaceToFavorites()
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void deleteCurrentPlaceFromFavoritesCall() {
        when(preferencesManager.getCurrentLatitude()).thenReturn(55.5f);
        when(preferencesManager.getCurrentLongitude()).thenReturn(34.5f);
        when(realmHelper.removeFavoritePlace(anyDouble(), anyDouble())).thenReturn(Completable.complete());

        weatherRepository.deleteCurrentPlaceFromFavorites()
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void checkIsCurrentPlaceFavorite() {
        when(preferencesManager.getCurrentLatitude()).thenReturn(55.5f);
        when(preferencesManager.getCurrentLongitude()).thenReturn(34.5f);
        when(realmHelper.checkCurrentPlaceInFavorites(anyDouble(), anyDouble())).thenReturn(Single.just(true));

        weatherRepository.checkIsCurrentPlaceFavorite()
                .test()
                .assertNoErrors()
                .assertValue(true);
    }

    private ForecastWeather provideRandomResponse() {
        return random(ForecastWeather.class);
    }

    private ForecastModel provideRandomModel() {
        return random(ForecastModel.class);
    }
}
