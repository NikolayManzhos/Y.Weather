package com.example.weather.presentation.main.suggest_screen;


import com.example.weather.data.entities.details.DetailsResponse;
import com.example.weather.presentation.main.common.BaseMainView;

public interface SuggestView extends BaseMainView {
    void showContainerData();
    void hideContainerData();
    void showRecyclerData();
    void hideRecyclerData();
    void showDetailsLoad();
    void hideDetailsLoad();
    void receivePlaceDetails(DetailsResponse detailsResponse);
    void showSuggestionList(SuggestViewModel suggestViewModel);
}
