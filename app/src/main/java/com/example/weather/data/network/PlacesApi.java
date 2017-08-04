package com.example.weather.data.network;

import com.example.weather.domain.entities.autocomplete.SuggestResponse;
import com.example.weather.domain.entities.details.DetailsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApi {

    @GET("autocomplete/json?types=(cities)")
    Single<SuggestResponse> getSuggestions(
            @Query("input") String query,
            @Query("language") String language,
            @Query("key") String apiKey);

    @GET("details/json")
    Single<DetailsResponse> getPlaceDetails(
            @Query("placeid") String placeId,
            @Query("key") String apiKey);
}
