package com.example.weather.presentation.main;

import android.util.Log;

import com.example.weather.domain.interactor.MainViewInteractor;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.presentation.di.scope.PerActivity;
import com.example.weather.presentation.main.common.BaseMainPresenter;
import com.example.weather.utils.GlobalConstants;
import com.example.weather.utils.rx.RxBus;

import javax.inject.Inject;

@PerActivity
public class MainPresenter extends BaseMainPresenter<MainView> {

    private MainViewInteractor mainViewInteractor;
    private RxBus rxBus;

    @Inject
    public MainPresenter(MainViewInteractor mainViewInteractor,
                         RxBus rxBus) {
        this.mainViewInteractor = mainViewInteractor;
        this.rxBus = rxBus;
    }

    @Override
    public void onAttach() {
        rxBus.subscribe(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED,
                this,
                message -> requestFavoriteItems());
    }

    @Override
    public void onDetach() {
        rxBus.unsubscribe(this);
    }

    public void selectedHome() {
        getRouter().showHomeScreen();
    }

    public void selectSuggestScreen() {
        getRouter().showSuggestScreen();
    }

    public void selectedSettings() {
        getRouter().showSettingsScreen();
    }

    public void selectedAboutApp() {
        getRouter().showAboutApplicationScreen();
    }

    public void requestFavoriteItems() {
        getCompositeDisposable().add(
                mainViewInteractor.requestFavoriteItems()
                .subscribe(
                        favoritePlaces -> {
                            if (getView() != null) {
                                getView().displayFavoriteItems(favoritePlaces);
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().showError();
                            }
                        }
                )
        );
    }

    public void changeCurrentPlace(FavoritePlace favoritePlace) {
        getCompositeDisposable().add(
                mainViewInteractor.changeCurrentPlace(favoritePlace)
                .subscribe(
                        this::selectedHome,
                        err -> {}
                )
        );
    }

    public void removePlaceFromFavorites(FavoritePlace favoritePlace, int position) {
        getCompositeDisposable().add(
                mainViewInteractor.removeItemFromDatabase(favoritePlace)
                .subscribe(
                        () -> {
                            if (getView() != null) {
                                getView().confirmFavoriteRemoved(position);
                                rxBus.publish(GlobalConstants.EVENT_FAVORITES_CHANGED, true);
                            }
                        },
                        err -> {
                            Log.d("MainPresenter", err.toString());
                        }
                )
        );
    }
}
