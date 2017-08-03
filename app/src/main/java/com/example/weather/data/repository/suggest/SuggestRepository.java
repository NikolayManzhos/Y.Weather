package com.example.weather.data.repository.suggest;


import com.example.weather.domain.entities.autocomplete.SuggestResponse;

import io.reactivex.Single;


public interface SuggestRepository {
    Single<SuggestResponse> getSuggestions(String query);
}
