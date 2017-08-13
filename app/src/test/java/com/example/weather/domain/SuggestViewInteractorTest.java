package com.example.weather.domain;


import com.example.weather.TestSchedulerProvider;
import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.repository.suggest.PlacesRepository;
import com.example.weather.domain.interactor.SuggestViewInteractor;
import com.example.weather.domain.interactor.SuggestViewInteractorImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import io.reactivex.Completable;
import io.reactivex.Single;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class SuggestViewInteractorTest {

    @Mock
    private PlacesRepository repository;

    private SuggestViewInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new SuggestViewInteractorImpl(repository, new TestSchedulerProvider());
    }

    @Test
    public void requestSuggestItemsCall() {
        SuggestResponse suggestResponse = provideRandomSuggestResponse();
        when(repository.getSuggestions(anyString())).thenReturn(Single.just(suggestResponse));

        String QUERY = "Mos";
        interactor.requestSuggestItems(QUERY)
                .test()
                .assertNoErrors()
                .assertValue(suggestResponse);
    }

    @Test
    public void requestPlaceDetailsCall() {
        when(repository.getPlaceDetails(anyString())).thenReturn(Completable.complete());

        String PLACE_ID = "ahb2Sjb2kiAUBWmQ";
        interactor.requestPlaceDetails(PLACE_ID)
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    private SuggestResponse provideRandomSuggestResponse() {
        return random(SuggestResponse.class);
    }
}
