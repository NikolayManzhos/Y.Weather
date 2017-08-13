package com.example.weather.domain.interactor;


import com.example.weather.domain.models.FavoritePlace;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MainViewInteractor  {
    Single<List<FavoritePlace>> requestFavoriteItems();
    Completable changeCurrentPlace(FavoritePlace favoritePlace);
    Completable removeItemFromDatabase(FavoritePlace favoritePlace);
}
