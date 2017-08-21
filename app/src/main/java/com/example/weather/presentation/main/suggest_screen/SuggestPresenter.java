package com.example.weather.presentation.main.suggest_screen;


import com.example.weather.domain.interactor.SuggestViewInteractor;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.common.BaseMainPresenter;

import javax.inject.Inject;

@PerFragment
public class SuggestPresenter extends BaseMainPresenter<SuggestView> {

    private SuggestViewInteractor interactor;

    @Inject
    SuggestPresenter(SuggestViewInteractor interactor) {
        this.interactor = interactor;
    }

    void getSuggestions(String query) {
        getView().showLoad();
        getView().hideRecyclerData();
        getCompositeDisposable().add(
                interactor.requestSuggestItems(query)
                .subscribe(
                        suggestResponse -> {
                            getView().hideLoad();
                            getView().showRecyclerData();
                            getView().showSuggestionList(SuggestViewModel.create(suggestResponse));
                        },
                        err -> {
                            getView().hideLoad();
                            getView().showError();
                        }
                )
        );
    }

    public void getPlaceDetails(String placeId) {
        getView().showDetailsLoad();
        getView().hideContainerData();
        getCompositeDisposable().add(
                interactor.requestPlaceDetails(placeId)
                .subscribe(
                        () -> {
                            getView().hideDetailsLoad();
                            getView().showContainerData();
                            getView().receivePlaceDetails();
                        },
                        err -> {
                            getView().hideDetailsLoad();
                            getView().showContainerData();
                            getView().showError();
                        }
                )
        );
    }

}
