package com.example.weather.presentation.main.home_screen;


import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.domain.models.CurrentWeatherModel;
import com.example.weather.domain.models.ForecastModel;
import com.example.weather.presentation.main.MainRouter;
import com.example.weather.presentation.main.common.ViewModelMapper;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;
import com.example.weather.utils.GlobalConstants;
import com.example.weather.utils.rx.MainScheduler;
import com.example.weather.utils.rx.RxBus;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static io.github.benas.randombeans.api.EnhancedRandom.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {

    @Mock
    private ViewModelMapper viewModelMapper;

    @Mock
    private CurrentWeatherInteractor interactor;

    @Mock
    private HomeView view;

    @Mock
    private MainRouter mainRouter;

    @Mock
    private RxBus rxBus;

    private HomePresenter presenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new HomePresenter(interactor, viewModelMapper, rxBus);
        presenter.setView(view);
        presenter.setRouter(mainRouter);
        testScheduler = new TestScheduler();
    }

    @Test
    public void getWeatherSuccess() {
        ForecastModel result = provideFilledModel();
        when(interactor.requestWeather(anyBoolean(), anyBoolean()))
                .thenReturn(Observable.just(result).subscribeOn(testScheduler));

        presenter.getCurrentWeather(true, false);
        verify(view).showLoad();

        testScheduler.triggerActions();

        verify(view).showWeather(any(HomeViewModel.class));
        verify(view).hideLoad();
    }

    @Test
    public void getWeatherFailure() {
        when(interactor.requestWeather(anyBoolean(), anyBoolean()))
                .thenReturn(Observable.error(new Exception("Something goes wrong")));

        presenter.getCurrentWeather(true, false);
        verify(view).showLoad();

        verify(view).showError();
        verify(view).hideLoad();
    }

    @Test
    public void showDetailScreen() {
        WeatherViewModel weatherViewModel = provideFilledWeatherViewModel();
        presenter.showDetailsScreen(weatherViewModel);
        verify(mainRouter).showDetailsScreen(weatherViewModel);
    }

    @Test
    public void detachPresenter() {
        presenter.onDetach();
        verify(rxBus).unsubscribe(presenter);
    }

    private ForecastModel provideFilledModel() {
        return random(ForecastModel.class);
    }

    private WeatherViewModel provideFilledWeatherViewModel() {
        return random(WeatherViewModel.class);
    }

    @Test
    public void addCurrentPlaceToFavoritesSuccess() {
        when(interactor.addToFavorites()).thenReturn(Completable.complete());
        presenter.addCurrentPlaceToFavorites();

        verify(view).setFavoriteStatus(true);
        verify(rxBus).publish(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED, true);
    }

    @Test
    public void addCurrentPlaceToFavoritesFailure() {
        when(interactor.addToFavorites()).thenReturn(Completable.error(new Throwable()));

        presenter.addCurrentPlaceToFavorites();

        verify(view).setFavoriteStatus(false);
        verify(view, never()).setFavoriteStatus(true);
        verify(rxBus, never()).publish(anyString(), any());
    }

    @Test
    public void removeCurrentPlaceFromFavoritesSuccess() {
        when(interactor.removeFromFavorites()).thenReturn(Completable.complete());

        presenter.removeCurrentPlaceFromFavorites();

        verify(view).setFavoriteStatus(false);
        verify(view, never()).showRemoveError();
    }

    @Test
    public void removeCurrentPlaceFromFavoritesFailure() {
        Throwable error = new Throwable();
        when(interactor.removeFromFavorites()).thenReturn(Completable.error(error));

        presenter.removeCurrentPlaceFromFavorites();

        verify(view).showRemoveError();
        verify(view, never()).setFavoriteStatus(false);
    }

    @Test
    public void checkCurrentFavoriteStatusSuccess() {
        when(interactor.checkCurrentPlaceInFavorites()).thenReturn(Single.just(true));

        presenter.checkCurrentPlaceFavoriteStatus();

        verify(view).setFavoriteStatus(true);
    }

    @Test
    public void checkCurrentFavoriteStatusFailure() {
        when(interactor.checkCurrentPlaceInFavorites()).thenReturn(Single.error(new Throwable()));

        presenter.checkCurrentPlaceFavoriteStatus();

        verify(view, never()).setFavoriteStatus(anyBoolean());
    }

    @Test
    public void onEventDispatch() {
        rxBus = new RxBus(Schedulers::trampoline);
        presenter = new HomePresenter(interactor, viewModelMapper, rxBus);
        when(interactor.checkCurrentPlaceInFavorites()).thenReturn(Single.just(true));

        presenter.onAttach();
        presenter.setView(view);
        rxBus.publish(GlobalConstants.EVENT_FAVORITES_CHANGED, true);

        verify(interactor).checkCurrentPlaceInFavorites();
    }
}
