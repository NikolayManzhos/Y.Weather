package com.example.weather.presentation.main;

import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.presentation.main.common.BaseMainView;

import java.util.List;

public interface MainView extends BaseMainView {
    void displayFavoriteItems(List<FavoritePlace> favoritePlaces);
    void confirmFavoriteRemoved(int position);
}
