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
        return realmHelper.queryFavoriteItems();
    }

    @Override
    public Completable setCurrentPlace(FavoritePlace favoritePlace) {
        return Completable.fromCallable(() -> {
            preferencesManager.setCurrentCityName(favoritePlace.getName());
            preferencesManager.setCurrentLatitude(favoritePlace.getLatitude());
            preferencesManager.setCurrentLongitude(favoritePlace.getLongitude());
            return true;
        });
    }

    @Override
    public Completable removeFavoriteItem(FavoritePlace favoritePlace) {
        return realmHelper.removeItem(favoritePlace);
    }
}
