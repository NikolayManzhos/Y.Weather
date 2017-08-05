package com.example.weather.domain.interactor;


import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.entities.details.DetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface SuggestViewInteractor {
    Single<SuggestResponse> requestSuggestItems(String query);
    Completable requestPlaceDetails(String placeId);
}
