package com.example.weather.presentation.main;


import com.example.weather.domain.interactor.MainViewInteractor;
import com.example.weather.domain.models.FavoritePlace;
import com.example.weather.utils.GlobalConstants;
import com.example.weather.utils.rx.RxBus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    @Mock
    private MainRouter router;

    @Mock
    private MainView view;

    @Mock
    private MainViewInteractor interactor;

    @Mock
    private RxBus rxBus;

    private MainPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(interactor, rxBus);
        presenter.setRouter(router);
        presenter.setView(view);
    }

    @Test
    public void checkHomeSelection() {
        presenter.selectedHome();
        verify(router).showHomeScreen();
    }

    @Test
    public void checkSuggestSelection() {
        presenter.selectSuggestScreen();
        verify(router).showSuggestScreen();
    }

    @Test
    public void checkSettingsSelection() {
        presenter.selectedSettings();
        verify(router).showSettingsScreen();
    }

    @Test
    public void checkAboutAppSelection() {
        presenter.selectedAboutApp();
        verify(router).showAboutApplicationScreen();
    }

    @Test
    public void requestFavoriteItemsSuccess() {
        List<FavoritePlace> favorites = new ArrayList<>();
        when(interactor.requestFavoriteItems()).thenReturn(Single.just(favorites));

        presenter.requestFavoriteItems();

        verify(view).displayFavoriteItems(favorites);
        verify(view, never()).showError();
    }

    @Test
    public void requestFavoriteItemsFailure() {
        Throwable error = new Throwable();
        when(interactor.requestFavoriteItems()).thenReturn(Single.error(error));

        presenter.requestFavoriteItems();

        verify(view).showError();
        verify(view, never()).displayFavoriteItems(anyListOf(FavoritePlace.class));
    }

    @Test
    public void changeCurrentPlaceSuccess() {
        FavoritePlace favoritePlace = provideRandomFavoritePlace();
        when(interactor.changeCurrentPlace(favoritePlace)).thenReturn(Completable.complete());

        presenter.changeCurrentPlace(favoritePlace);

        verify(router).showHomeScreen();
    }

    @Test
    public void changeCurrentPlaceFailure() {
        when(interactor.changeCurrentPlace(any(FavoritePlace.class)))
                .thenReturn(Completable.error(new Throwable()));

        presenter.changeCurrentPlace(provideRandomFavoritePlace());

        verify(router, never()).showHomeScreen();
    }

    @Test
    public void onDetachUnSubscribe() {
        presenter.onDetach();

        verify(rxBus).unsubscribe(presenter);
    }

    @Test
    public void removePlaceFromFavoritesSuccess() {
        FavoritePlace randomFavPlace = provideRandomFavoritePlace();
        int aPosition = 1;
        when(interactor.removeItemFromDatabase(randomFavPlace)).thenReturn(Completable.complete());

        presenter.removePlaceFromFavorites(randomFavPlace, aPosition);
        verify(view).confirmFavoriteRemoved(aPosition);
        verify(rxBus).publish(GlobalConstants.EVENT_FAVORITES_CHANGED, true);
        verify(view, never()).showFavRemoveError();
    }

    @Test
    public void removePlaceFromFavoritesFailure() {
        when(interactor.removeItemFromDatabase(any(FavoritePlace.class)))
                .thenReturn(Completable.error(new Throwable()));

        presenter.removePlaceFromFavorites(provideRandomFavoritePlace(), 2);

        verify(view).showFavRemoveError();
        verify(view, never()).confirmFavoriteRemoved(anyInt());
        verify(rxBus, never()).publish(GlobalConstants.EVENT_FAVORITES_CHANGED, true);
    }

    @Test
    public void onEventDispatch() {
        rxBus = new RxBus(Schedulers::trampoline);
        presenter = new MainPresenter(interactor, rxBus);
        when(interactor.requestFavoriteItems()).thenReturn(Single.just(new ArrayList<>()));

        presenter.onAttach();
        rxBus.publish(GlobalConstants.EVENT_FAVORITE_ADDED_REMOVED, true);

        verify(interactor).requestFavoriteItems();
    }

    private FavoritePlace provideRandomFavoritePlace() {
        return random(FavoritePlace.class);
    }
}
