package com.example.weather.domain.interactor;


import com.example.weather.domain.entities.autocomplete.SuggestResponse;

import io.reactivex.Observable;

public interface SuggestViewInteractor {
    Observable<SuggestResponse> requestSuggestItems(String query, boolean force);
}
