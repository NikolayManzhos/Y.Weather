package com.example.weather.data.repository;


import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
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
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainRepositoryTest {

    @Mock
    private RealmHelper realmHelper;

    @Mock
    private PreferencesManager preferencesManager;

    private MainViewRepositoryImpl mainViewRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mainViewRepository = new MainViewRepositoryImpl(realmHelper, preferencesManager);
    }

    @Test
    public void getFavoriteItemsCall() {
        List<FavoritePlace> databaseResponse = new ArrayList<>();
        Single<List<FavoritePlace>> single = Single.just(databaseResponse);
        when(realmHelper.queryAllFavoriteItems()).thenReturn(single);

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
                .thenReturn(favoritePlace.getLatitude());
        when(preferencesManager.getCurrentLongitude())
                .thenReturn(favoritePlace.getLongitude());
        when(realmHelper.checkCurrentPlaceInFavorites(anyDouble(), anyDouble()))
                .thenReturn(Single.just(false));

        mainViewRepository.setCurrentPlace(favoritePlace)
                .test()
                .assertNoErrors()
                .assertComplete();
        verify(realmHelper).removeForecast(anyDouble(), anyDouble());
    }

    @Test
    public void removeCurrentFavoriteItem() {
        FavoritePlace placeToRemove = provideRandomFavoritePlace();
        when(preferencesManager.getCurrentLatitude()).thenReturn(placeToRemove.getLatitude() + 1);
        when(preferencesManager.getCurrentLongitude()).thenReturn(placeToRemove.getLongitude() + 1);
        when(realmHelper.removeFavoritePlace(placeToRemove.getLatitude(), placeToRemove.getLongitude(), false))
                .thenReturn(Completable.complete());

        mainViewRepository.removeFavoriteItem(placeToRemove)
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    @Test
    public void removeFavoriteItem() {
        FavoritePlace placeToRemove = provideRandomFavoritePlace();
        when(preferencesManager.getCurrentLatitude()).thenReturn(placeToRemove.getLatitude());
        when(preferencesManager.getCurrentLongitude()).thenReturn(placeToRemove.getLongitude());
        when(realmHelper.removeFavoritePlace(placeToRemove.getLatitude(), placeToRemove.getLongitude(), true))
                .thenReturn(Completable.complete());

        mainViewRepository.removeFavoriteItem(placeToRemove)
                .test()
                .assertNoErrors()
                .assertComplete();
    }

    private FavoritePlace provideRandomFavoritePlace() {
        return random(FavoritePlace.class);
    }
}
