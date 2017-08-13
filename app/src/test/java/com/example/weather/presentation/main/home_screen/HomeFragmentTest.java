package com.example.weather.presentation.main.home_screen;


import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HomeFragmentTest {

    @Test
    public void callPresenterOnRefresh() {
        HomeFragment homeFragment = HomeFragment.newInstance();
        HomePresenter presenter = mock(HomePresenter.class);

        homeFragment.homePresenter = presenter;
        homeFragment.onRefresh();
        verify(presenter).getCurrentWeather(true, false);
    }
}
