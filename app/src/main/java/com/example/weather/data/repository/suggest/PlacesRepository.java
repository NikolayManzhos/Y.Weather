package com.example.weather.data.repository.suggest;


import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.entities.details.DetailsResponse;

import io.reactivex.Single;


public interface PlacesRepository {
    Single<SuggestResponse> getSuggestions(String query);
    Single<DetailsResponse> getPlaceDetails(String placeId);
}
