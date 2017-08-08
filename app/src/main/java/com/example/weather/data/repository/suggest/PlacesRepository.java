package com.example.weather.data.repository.suggest;


import com.example.weather.data.entities.autocomplete.SuggestResponse;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface PlacesRepository {
    Single<SuggestResponse> getSuggestions(String query);
    Completable getPlaceDetails(String placeId);
}
