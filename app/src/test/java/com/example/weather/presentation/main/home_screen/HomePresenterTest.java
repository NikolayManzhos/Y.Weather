package com.example.weather.presentation.main.home_screen;


import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.models.CurrentWeatherModel;
import com.example.weather.domain.models.ForecastModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static io.github.benas.randombeans.api.EnhancedRandom.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {


    @Mock
    private CurrentWeatherInteractor interactor;

    @Mock
    private HomeView view;

    private HomePresenter presenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new HomePresenter(interactor);
        presenter.setView(view);
        testScheduler = new TestScheduler();
    }

    @Test
    public void getWeatherSuccess() {
        ForecastModel result = provideFilledModel();
        when(interactor.requestWeather(anyBoolean()))
                .thenReturn(Observable.just(result).subscribeOn(testScheduler));

        presenter.getCurrentWeather(true);
        verify(view).showLoad();

        testScheduler.triggerActions();

        verify(view).showWeather(result);
        verify(view).hideLoad();
    }

    @Test
    public void getWeatherFailure() {
        when(interactor.requestWeather(anyBoolean()))
                .thenReturn(Observable.error(new Exception("Something goes wrong")));

        presenter.getCurrentWeather(true);
        verify(view).showLoad();

        verify(view).showError(anyInt());
        verify(view).hideLoad();
    }

    @Test
    public void detachPresenter() {
        presenter.onDetach();
    }

    private ForecastModel provideFilledModel() {
        return random(ForecastModel.class);
    }
}
