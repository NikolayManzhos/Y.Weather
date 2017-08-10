package com.example.weather.data.local;

import android.util.Log;

import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.domain.models.ForecastModel;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

@Singleton
public class RealmHelper {

    @Inject
    public RealmHelper() {}

    public void writeForecast(ForecastModel forecastModel) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realmInstance -> realmInstance.insertOrUpdate(forecastModel));
        realm.close();
    }

    public Single<ForecastModel> readForecast(double latitude, double longitude) {
        return Single.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            ForecastModel forecastModel = realm.where(ForecastModel.class)
                    .equalTo("primaryKey", "forecast")
                    .equalTo("latitude", latitude)
                    .equalTo("longitude", longitude)
                    .findFirst();
            if (forecastModel != null) {
                Log.d("RealmHelper", "not null");
                ForecastModel finalData = realm.copyFromRealm(forecastModel);
                realm.close();
                return finalData;
            }
            realm.close();
            throw new NoSuchElementException("No matching element in database");
        });
    }

    public Completable writeFavoritePlace(FavoritePlace favoritePlace) {
        return Completable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction -> {
                transaction.insertOrUpdate(favoritePlace);
            });
            realm.close();
            return true;
        });
    }

    public Completable removeFavoritePlace(double latitude, double longitude) {
        return Completable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction -> {
                FavoritePlace realmObject = transaction.where(FavoritePlace.class)
                        .equalTo("latitude", latitude)
                        .equalTo("longitude", longitude)
                        .findFirst();
                realmObject.deleteFromRealm();
            });
            realm.close();
            return true;
        });
    }

    public Single<Boolean> checkCurrentPlaceInFavorites(double latitude, double longitude) {
        return Single.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            FavoritePlace place = realm.where(FavoritePlace.class)
                    .equalTo("latitude", latitude)
                    .equalTo("longitude", longitude)
                    .findFirst();
            return place != null;
        });
    }

    public Single<List<FavoritePlace>> queryFavoriteItems() {
        return Single.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<FavoritePlace> favPlaces = realm
                    .where(FavoritePlace.class)
                    .findAll();
            List<FavoritePlace> favPlacesList = realm.copyFromRealm(favPlaces);
            realm.close();
            return favPlacesList;
        });
    }

    public Completable removeItem(FavoritePlace favoritePlace) {
        return Completable.fromCallable(() -> {
            Realm realm = Realm.getDefaultInstance();
            realm.executeTransaction(transaction -> {
                FavoritePlace realmObject = realm.where(FavoritePlace.class)
                        .equalTo("primaryKey",favoritePlace.getPrimaryKey())
                        .findFirst();
                realmObject.deleteFromRealm();
            });
            realm.close();
            return true;
        });
    }
}
