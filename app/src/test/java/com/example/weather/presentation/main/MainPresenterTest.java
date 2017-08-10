package com.example.weather.presentation.main;


import com.example.weather.domain.interactor.MainViewInteractor;
import com.example.weather.utils.rx.RxBus;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    @Mock
    private MainRouter router;

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
}
