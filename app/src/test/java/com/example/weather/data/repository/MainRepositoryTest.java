package com.example.weather.data.repository;


import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.repository.main.MainViewRepository;
import com.example.weather.data.repository.main.MainViewRepositoryImpl;
import com.example.weather.domain.models.FavoritePlace;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.when;

public class MainRepositoryTest {

    @Mock
    private RealmHelper realmHelper;

    @Mock
    private PreferencesManager preferencesManager;

    private MainViewRepository mainViewRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainViewRepository = new MainViewRepositoryImpl(realmHelper, preferencesManager);
    }

    @Test
    public void getFavoriteItemsCall() {
        List<FavoritePlace> databaseResponse = new ArrayList<>();
        Single<List<FavoritePlace>> single = Single.just(databaseResponse);
        when(realmHelper.queryFavoriteItems()).thenReturn(single);

        mainViewRepository.getFavoriteItems()
                .test()
                .assertNoErrors()
                .assertValue(databaseResponse);
    }

    @Test
    public void setCurrentPlaceCall() {
        FavoritePlace favoritePlace = provideRandomFavoritePlace();
        when(preferencesManager.getCurrentCityName())
                .thenReturn(favoritePlace.getName());
        when(preferencesManager.getCurrentLatitude())
                .thenReturn((float) favoritePlace.getLatitude());
        when(preferencesManager.getCurrentLongitude())
                .thenReturn((float) favoritePlace.getLongitude());

        mainViewRepository.setCurrentPlace(favoritePlace)
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void removeFavoriteCall() {
        FavoritePlace placeToRemove = provideRandomFavoritePlace();
        when(realmHelper.removeItem(placeToRemove)).thenReturn(Completable.complete());

        mainViewRepository.removeFavoriteItem(placeToRemove)
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    private FavoritePlace provideRandomFavoritePlace() {
        return random(FavoritePlace.class);
    }
}
