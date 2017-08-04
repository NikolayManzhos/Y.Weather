package com.example.weather.presentation.main.suggest_screen;


import com.example.weather.presentation.main.common.BaseMainView;

public interface SuggestView extends BaseMainView {
    void showData();
    void hideData();
    void showSuggestionList(SuggestViewModel suggestViewModel);
}
