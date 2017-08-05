package com.example.weather.presentation.main.suggest_screen;

import com.example.weather.data.entities.autocomplete.Prediction;
import com.example.weather.data.entities.autocomplete.SuggestResponse;

import java.util.List;

public class SuggestViewModel {

    public static SuggestViewModel create(SuggestResponse suggestResponse) {
        return new SuggestViewModel(suggestResponse.getPredictions());
    }

    private SuggestViewModel(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    private List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }
}
