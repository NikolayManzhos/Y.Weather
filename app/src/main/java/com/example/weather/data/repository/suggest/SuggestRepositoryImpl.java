package com.example.weather.data.repository.suggest;


import com.example.weather.BuildConfig;
import com.example.weather.data.network.PlacesApi;
import com.example.weather.domain.entities.autocomplete.SuggestResponse;

import java.util.Locale;

import io.reactivex.Single;

public class SuggestRepositoryImpl implements SuggestRepository {

    private PlacesApi placesApi;
    public SuggestRepositoryImpl(PlacesApi placesApi) {
        this.placesApi = placesApi;
    }

    @Override
    public Single<SuggestResponse> getSuggestions(String query) {
        return placesApi.getSuggestions(query,
                Locale.getDefault().toString(),
                BuildConfig.PLACES_KEY);
    }
}
