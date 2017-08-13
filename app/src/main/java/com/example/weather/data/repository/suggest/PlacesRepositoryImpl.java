package com.example.weather.data.repository.suggest;


import com.example.weather.BuildConfig;
import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.entities.details.Location;
import com.example.weather.data.entities.details.Result;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.network.PlacesApi;

import java.util.Locale;

import io.reactivex.Completable;
import io.reactivex.Single;

public class PlacesRepositoryImpl implements PlacesRepository {

    private PlacesApi placesApi;
    private PreferencesManager preferencesManager;
    private RealmHelper realmHelper;

    public PlacesRepositoryImpl(PlacesApi placesApi,
                                PreferencesManager preferencesManager,
                                RealmHelper realmHelper) {
        this.placesApi = placesApi;
        this.preferencesManager = preferencesManager;
        this.realmHelper = realmHelper;
    }

    @Override
    public Single<SuggestResponse> getSuggestions(String query) {
        return placesApi.getSuggestions(query,
                Locale.getDefault().toString(),
                BuildConfig.PLACES_KEY);
    }

    @Override
    public Completable getPlaceDetails(String placeId) {
        return placesApi.getPlaceDetails(placeId,
                Locale.getDefault().toString(),
                BuildConfig.PLACES_KEY)
                .doOnSuccess(detailsResponse -> {
                    Result result = detailsResponse.getResult();
                    Location location = result.getGeometry().getLocation();
                    double currentLatitude = preferencesManager.getCurrentLatitude();
                    double currentLongitude = preferencesManager.getCurrentLongitude();
                    realmHelper.checkCurrentPlaceInFavorites(
                            currentLatitude,
                            currentLongitude)
                            .subscribe(isFavorite -> {
                                if (!isFavorite) realmHelper.removeForecast(currentLatitude, currentLongitude);
                            });
                    preferencesManager.setCurrentCityName(result.getVicinity());
                    preferencesManager.setCurrentLatitude(location.getLat());
                    preferencesManager.setCurrentLongitude(location.getLng());
                })
                .toCompletable();
    }
}
