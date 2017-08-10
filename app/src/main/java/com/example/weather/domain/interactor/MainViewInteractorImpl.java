package com.example.weather.domain.interactor;

import com.example.weather.data.repository.main.MainViewRepository;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class MainViewInteractorImpl implements MainViewInteractor {

    private MainViewRepository mainViewRepository;
    private SchedulerProvider schedulerProvider;

    @Inject
    public MainViewInteractorImpl(MainViewRepository mainViewRepository,
                                  SchedulerProvider schedulerProvider) {
        this.mainViewRepository = mainViewRepository;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Single<List<FavoritePlace>> requestFavoriteItems() {
        return mainViewRepository.getFavoriteItems()
                .compose(schedulerProvider.applyIoSchedulers());
    }

    @Override
    public Completable changeCurrentPlace(FavoritePlace favoritePlace) {
        return mainViewRepository.setCurrentPlace(favoritePlace);
    }

    @Override
    public Completable removeItemFromDatabase(FavoritePlace favoritePlace) {
        return mainViewRepository.removeFavoriteItem(favoritePlace)
                .compose(schedulerProvider.applyIoSchedulersCompletable());
    }
}
