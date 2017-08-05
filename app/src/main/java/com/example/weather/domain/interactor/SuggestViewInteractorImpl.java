package com.example.weather.domain.interactor;

import com.example.weather.data.repository.suggest.PlacesRepository;
import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.entities.details.DetailsResponse;
import com.example.weather.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class SuggestViewInteractorImpl implements SuggestViewInteractor {

    private PlacesRepository placesRepository;
    private SchedulerProvider schedulerProvider;

    @Inject
    public SuggestViewInteractorImpl(PlacesRepository placesRepository,
                                     SchedulerProvider schedulerProvider) {
        this.placesRepository = placesRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<SuggestResponse> requestSuggestItems(String query) {
        return placesRepository.getSuggestions(query)
                .compose(schedulerProvider.applyIoSchedulers());
    }

    @Override
    public Completable requestPlaceDetails(String placeId) {
        return placesRepository.getPlaceDetails(placeId)
                .compose(schedulerProvider.applyIoSchedulersCompletable());
    }
}
