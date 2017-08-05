package com.example.weather.data.local;

import com.example.weather.domain.models.ForecastModel;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    public ForecastModel readForecast(double latitude, double longitude) {
        Realm realm = Realm.getDefaultInstance();
        String primaryKey = String.valueOf(latitude) + String.valueOf(longitude);
        ForecastModel forecastModel = realm.where(ForecastModel.class).equalTo("primaryKey", primaryKey).findFirst();
        if (forecastModel != null) {
            ForecastModel finalData = realm.copyFromRealm(forecastModel);
            realm.close();
            return finalData;
        }
        realm.close();
        return new ForecastModel();
    }
}
