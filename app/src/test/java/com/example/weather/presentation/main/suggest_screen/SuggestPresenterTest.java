package com.example.weather.presentation.main.suggest_screen;


import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.domain.interactor.SuggestViewInteractor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SuggestPresenterTest {

    @Mock
    private SuggestViewInteractor interactor;

    @Mock
    private SuggestView view;

    private SuggestPresenter presenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new SuggestPresenter(interactor);
        presenter.setView(view);
        testScheduler = new TestScheduler();
    }

    @Test
    public void getSuggestionsSuccess() {
        Single<SuggestResponse> single = Single.just(new SuggestResponse()).subscribeOn(testScheduler);
        when(interactor.requestSuggestItems(anyString())).thenReturn(single);

        presenter.getSuggestions("sHJ691hBI2naa");

        verify(view).showLoad();
        verify(view).hideRecyclerData();

        testScheduler.triggerActions();

        verify(view).hideLoad();
        verify(view).showRecyclerData();
        verify(view).showSuggestionList(any(SuggestViewModel.class));
    }
}
