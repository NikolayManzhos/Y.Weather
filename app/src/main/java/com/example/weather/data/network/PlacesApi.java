package com.example.weather.data.network;

import com.example.weather.domain.entities.autocomplete.SuggestResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesApi {

    @GET("json?types=(cities)")
    Single<SuggestResponse> getSuggestions(
            @Query("input") String query,
            @Query("language") String language,
            @Query("key") String apiKey);

    @GET("details/json")
    Single<?> getPlaceDetails(
            @Query("placeid") String placeId,
            @Query("key") String apiKey);

}
