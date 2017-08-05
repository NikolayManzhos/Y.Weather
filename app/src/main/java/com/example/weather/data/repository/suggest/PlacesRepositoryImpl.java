package com.example.weather.data.repository.suggest;


import com.example.weather.BuildConfig;
import com.example.weather.data.entities.details.Location;
import com.example.weather.data.entities.details.Result;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.network.PlacesApi;
import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.entities.details.DetailsResponse;

import java.util.Locale;

import io.reactivex.Single;

public class PlacesRepositoryImpl implements PlacesRepository {

    private PlacesApi placesApi;
    private PreferencesManager preferencesManager;

    public PlacesRepositoryImpl(PlacesApi placesApi,
                                PreferencesManager preferencesManager) {
        this.placesApi = placesApi;
        this.preferencesManager = preferencesManager;
    }

    @Override
    public Single<SuggestResponse> getSuggestions(String query) {
        return placesApi.getSuggestions(query,
                Locale.getDefault().toString(),
                BuildConfig.PLACES_KEY);
    }

    @Override
    public Single<DetailsResponse> getPlaceDetails(String placeId) {
        return placesApi.getPlaceDetails(placeId,
                Locale.getDefault().toString(),
                BuildConfig.PLACES_KEY)
                .doOnSuccess(detailsResponse -> {
                    Result result = detailsResponse.getResult();
                    Location location = result.getGeometry().getLocation();
                    preferencesManager.setCurrentCityName(result.getVicinity());
                    preferencesManager.setCurrentLatitude(location.getLat());
                    preferencesManager.setCurrentLongitude(location.getLng());
                });
    }
}
