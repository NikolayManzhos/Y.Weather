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

    public void getSuggestions(String query) {
        if (getView() != null) {
            getView().showLoad();
            getView().hideRecyclerData();
        }
        getCompositeDisposable().add(
                interactor.requestSuggestItems(query)
                .subscribe(
                        suggestResponse -> {
                            if (getView() != null) {
                                getView().hideLoad();
                                getView().showRecyclerData();
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

    public void getPlaceDetails(String placeId) {
        if (getView() != null) {
            getView().showDetailsLoad();
            getView().hideContainerData();
        }
        getCompositeDisposable().add(
                interactor.requestPlaceDetails(placeId)
                .subscribe(
                        () -> {
                            if (getView() != null) {
                                getView().hideDetailsLoad();
                                getView().showContainerData();
                                getView().receivePlaceDetails();
                            }
                        },
                        err -> {
                            if (getView() != null) {
                                getView().hideDetailsLoad();
                                getView().showContainerData();
                                //TODO Handle error
                            }
                        }
                )
        );
    }

}
