package com.example.weather.presentation.main.suggest_screen;


import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SuggestFragmentTest {

    @Test
    public void callPresenterOnPlaceSelected() {
        SuggestFragment fragment = SuggestFragment.newInstance();
        SuggestPresenter presenter = mock(SuggestPresenter.class);
        fragment.presenter = presenter;
        String PLACE_ID = "2kaUWBBAA82jSA";

        fragment.placeClicked(PLACE_ID);
        verify(presenter).getPlaceDetails(PLACE_ID);
    }
}
