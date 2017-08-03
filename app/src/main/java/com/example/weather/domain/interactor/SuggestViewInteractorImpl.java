package com.example.weather.domain.interactor;


import com.example.weather.data.repository.suggest.SuggestRepository;
import com.example.weather.domain.entities.autocomplete.SuggestResponse;
import com.example.weather.utils.rx.SchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.ReplaySubject;

public class SuggestViewInteractorImpl implements SuggestViewInteractor {

    private Disposable suggestDisposable;
    private ReplaySubject<SuggestResponse> suggestReplaySubject;

    private SuggestRepository suggestRepository;
    private SchedulerProvider schedulerProvider;

    public SuggestViewInteractorImpl(SuggestRepository suggestRepository,
                                     SchedulerProvider schedulerProvider) {
        this.suggestRepository = suggestRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Observable<SuggestResponse> requestSuggestItems(String query, boolean force) {
        if (force && suggestDisposable != null) suggestDisposable.dispose();
        if (suggestDisposable == null || suggestDisposable.isDisposed()) {
            suggestReplaySubject = ReplaySubject.create(1);

            suggestDisposable = suggestRepository.getSuggestions(query)
                    .compose(schedulerProvider.applyIoSchedulers())
                    .subscribe(suggestReplaySubject::onNext, suggestReplaySubject::onError);
        }
        return suggestReplaySubject;
    }
}
