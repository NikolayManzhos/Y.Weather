package com.example.weather.presentation.main.suggest_screen;


import com.example.weather.domain.interactor.SuggestViewInteractor;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.common.BaseMainPresenter;

import javax.inject.Inject;

@PerFragment
public class SuggestPresenter extends BaseMainPresenter<SuggestView> {

    private SuggestViewInteractor interactor;

    @Inject
    public SuggestPresenter(SuggestViewInteractor interactor) {
        this.interactor = interactor;
    }

    public void getSuggestions(String query, boolean force) {
        if (getView() != null) {
            getView().showLoad();
            getView().hideData();
        }
        getCompositeDisposable().add(
                interactor.requestSuggestItems(query, force)
                .subscribe(
                        suggestResponse -> {
                            if (getView() != null) {
                                getView().hideLoad();
                                getView().showData();
                                getView().showSuggestionList(SuggestViewModel.create(suggestResponse));
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideLoad();
                                //TODO Handle error
                            }
                        }
                )
        );
    }

}
