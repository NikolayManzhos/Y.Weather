package com.example.weather.domain;


import com.example.weather.TestSchedulerProvider;
import com.example.weather.data.repository.main.MainViewRepository;
import com.example.weather.domain.interactor.MainViewInteractor;
import com.example.weather.domain.interactor.MainViewInteractorImpl;
import com.example.weather.domain.models.FavoritePlace;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MainViewInteractorTest {

    @Mock
    private MainViewRepository repository;

    private MainViewInteractor interactor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        interactor = new MainViewInteractorImpl(repository, new TestSchedulerProvider());
    }

    @Test
    public void requestFavoriteItemsSuccess() {
        List<FavoritePlace> favoritePlaces = new ArrayList<>();
        Single<List<FavoritePlace>> single = Single.just(favoritePlaces);
        when(repository.getFavoriteItems()).thenReturn(single);

        interactor.requestFavoriteItems()
                .test()
                .assertNoErrors()
                .assertValue(favoritePlaces);
    }

    @Test
    public void changeCurrentPlaceSuccess() {
        when(repository.setCurrentPlace(any(FavoritePlace.class))).thenReturn(Completable.complete());

        interactor.changeCurrentPlace(new FavoritePlace())
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void removeItemFromDatabaseSuccess() {
        when(repository.removeFavoriteItem(any(FavoritePlace.class))).thenReturn(Completable.complete());

        interactor.removeItemFromDatabase(new FavoritePlace())
                .test()
                .assertNoErrors()
                .assertComplete();
    }
}
