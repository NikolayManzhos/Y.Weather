package com.example.weather.data.repository.main;



import com.example.weather.domain.models.FavoritePlace;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MainViewRepository {
    Single<List<FavoritePlace>> getFavoriteItems();
    Completable setCurrentPlace(FavoritePlace favoritePlace);
    Completable removeFavoriteItem(FavoritePlace favoritePlace);
}
