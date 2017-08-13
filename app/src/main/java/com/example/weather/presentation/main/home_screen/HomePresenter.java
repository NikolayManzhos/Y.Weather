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
    public static final String TAG = "tag_home_presenter";

    private CurrentWeatherInteractor currentWeatherInteractor;
    private ViewModelMapper viewMapper;
    private RxBus rxBus;

    @Inject
    public HomePresenter(CurrentWeatherInteractor currentWeatherInteractor,
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

    public void getCurrentWeather(boolean force, boolean checkForCityChange) {
        if (getView() != null) {
            getView().showLoad();
        }
        getCompositeDisposable().add(
                currentWeatherInteractor.requestWeather(force, checkForCityChange).subscribe(
                        forecastModel -> {
                            if (getView() != null) {
                                getView().showWeather(viewMapper.forecastModelToViewModel(forecastModel));
                                getView().hideLoad();
                            }
                        }, throwable -> {
                            if (getView() != null) {
                                getView().showError();
                                getView().hideLoad();
                            }
                        }
                )
        );
    }

    public void showDetailsScreen(WeatherViewModel weatherViewModel) {
        getRouter().showDetailsScreen(weatherViewModel);
    }

    public void addCurrentPlaceToFavorites() {
        getCompositeDisposable().add(
                currentWeatherInteractor.addToFavorites()
                .subscribe(
                        () -> {
                            if (getView() != null) getView().setFavoriteStatus(true);
                                rxBus.publish(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED, true);
                        },
                        err -> {
                            if (getView() != null) getView().setFavoriteStatus(false);
                        }
                )
        );
    }

    public void removeCurrentPlaceFromFavorites() {
        getCompositeDisposable().add(
                currentWeatherInteractor.removeFromFavorites()
                .subscribe(
                        () -> {
                            if (getView() != null) getView().setFavoriteStatus(false);
                            rxBus.publish(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED, true);
                        },
                        err -> {
                            if (getView() != null) getView().showRemoveError();
                        }
                )
        );
    }

    public void checkCurrentPlaceFavoriteStatus() {
        getCompositeDisposable().add(
                currentWeatherInteractor.checkCurrentPlaceInFavorites()
                .subscribe(
                        status -> {
                            if (getView() != null) getView().setFavoriteStatus(status);
                        },
                        err -> {}
                )
        );
    }
}
