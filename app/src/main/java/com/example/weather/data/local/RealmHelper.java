package com.example.weather.data.local;

import android.util.Log;

import com.example.weather.domain.models.ForecastModel;

import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.realm.Realm;

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
}
