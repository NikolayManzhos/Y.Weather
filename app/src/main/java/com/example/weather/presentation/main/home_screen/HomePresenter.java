package com.example.weather.presentation.main.home_screen;


import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.common.BaseMainPresenter;
import com.example.weather.presentation.main.common.ViewModelMapper;
import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;
import com.example.weather.utils.GlobalConstants;
import com.example.weather.utils.rx.RxBus;

import javax.inject.Inject;


@PerFragment
public class HomePresenter extends BaseMainPresenter<HomeView> {

    private CurrentWeatherInteractor currentWeatherInteractor;
    private ViewModelMapper viewMapper;
    private RxBus rxBus;

    @Inject
    HomePresenter(CurrentWeatherInteractor currentWeatherInteractor,
                         ViewModelMapper viewMapper,
                         RxBus rxBus) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.viewMapper = viewMapper;
        this.rxBus = rxBus;
    }

    @Override
    public void onAttach() {
        rxBus.subscribe(GlobalConstants.EVENT_FAVORITES_CHANGED,
                this,
                message -> checkCurrentPlaceFavoriteStatus());
    }

    @Override
    public void onDetach() {
        rxBus.unsubscribe(this);
    }

    void getCurrentWeather(boolean force, boolean checkForCityChange) {
        getView().showLoad();
        getCompositeDisposable().add(
                currentWeatherInteractor.requestWeather(force, checkForCityChange).subscribe(
                        forecastModel -> {
                            getView().showWeather(viewMapper.forecastModelToViewModel(forecastModel));
                            getView().hideLoad();
                        }, throwable -> {
                            getView().showError();
                            getView().hideLoad();
                        }
                )
        );
    }

    void showDetailsScreen(WeatherViewModel weatherViewModel) {
        getRouter().showDetailsScreen(weatherViewModel);
    }

    void addCurrentPlaceToFavorites() {
        getCompositeDisposable().add(
                currentWeatherInteractor.addToFavorites()
                .subscribe(
                        () -> {
                            getView().setFavoriteStatus(true);
                            rxBus.publish(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED, true);
                        },
                        err -> getView().setFavoriteStatus(false)
                )
        );
    }

    void removeCurrentPlaceFromFavorites() {
        getCompositeDisposable().add(
                currentWeatherInteractor.removeFromFavorites()
                .subscribe(
                        () -> {
                            getView().setFavoriteStatus(false);
                            rxBus.publish(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED, true);
                        },
                        err -> getView().showRemoveError()
                )
        );
    }

    void checkCurrentPlaceFavoriteStatus() {
        getCompositeDisposable().add(
                currentWeatherInteractor.checkCurrentPlaceInFavorites()
                .subscribe(
                        status -> getView().setFavoriteStatus(status),
                        err -> {}
                )
        );
    }
}
