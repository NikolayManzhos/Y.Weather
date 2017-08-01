package com.example.weather.presentation.main.home_screen;


import com.example.weather.domain.GetCurrentWeatherInteractor;
import com.example.weather.domain.entities.DetailedWeather;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import io.github.benas.randombeans.api.EnhancedRandom;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {


    @Mock
    private GetCurrentWeatherInteractor interactor;

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
    public void callInteractorOnAttach() {
        when(interactor.requestWeather(false))
                .thenReturn(Observable.just(new DetailedWeather()));
        presenter.onAttach();

        verify(interactor).requestWeather(false);
    }

    @Test
    public void getWeatherSuccess() {
        DetailedWeather result = provideDetailedWeather();
        when(interactor.requestWeather(anyBoolean()))
                .thenReturn(Observable.just(result).subscribeOn(testScheduler));

        presenter.getCurrentWeather(true);
        verify(view).showLoad();

        testScheduler.triggerActions();

        verify(view).showWeather(any(HomeViewModel.class));
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

    private DetailedWeather provideDetailedWeather() {
        return EnhancedRandom.random(DetailedWeather.class);
    }
}
