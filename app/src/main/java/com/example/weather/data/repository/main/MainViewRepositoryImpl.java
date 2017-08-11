package com.example.weather.data.repository.main;

import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.domain.models.FavoritePlace;

import java.util.List;


import io.reactivex.Completable;
import io.reactivex.Single;

public class MainViewRepositoryImpl implements MainViewRepository {

    private RealmHelper realmHelper;
    private PreferencesManager preferencesManager;

    public MainViewRepositoryImpl(RealmHelper realmHelper,
                                  PreferencesManager preferencesManager) {
        this.realmHelper = realmHelper;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public Single<List<FavoritePlace>> getFavoriteItems() {
        return realmHelper.queryAllFavoriteItems();
    }

    @Override
    public Completable setCurrentPlace(FavoritePlace favoritePlace) {
        return Completable.fromCallable(() -> {
            preferencesManager.setCurrentCityName(favoritePlace.getName());
            preferencesManager.setCurrentLatitude(favoritePlace.getLatitude());
            preferencesManager.setCurrentLongitude(favoritePlace.getLongitude());
            return true;
        }).doOnSubscribe(disposable -> {
            double currentLatitude = preferencesManager.getCurrentLatitude();
            double currentLongitude = preferencesManager.getCurrentLongitude();
            realmHelper.checkCurrentPlaceInFavorites(currentLatitude, currentLongitude)
                    .subscribe(isFavorite -> {
                        if (!isFavorite) {
                            realmHelper.removeForecast(currentLatitude, currentLongitude);
                        }
                    }
                    );
        });
    }

    @Override
    public Completable removeFavoriteItem(FavoritePlace favoritePlace) {
        boolean isCurrentPlace = favoritePlace.getLatitude() == preferencesManager.getCurrentLatitude()
                && favoritePlace.getLongitude() == preferencesManager.getCurrentLongitude();
        return realmHelper.removeFavoritePlace(favoritePlace.getLatitude(), favoritePlace.getLongitude(), isCurrentPlace);
    }
}
