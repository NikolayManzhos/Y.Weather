package com.example.weather.presentation.main.home_screen;



import com.example.weather.R;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;
import com.example.weather.presentation.di.scope.PerFragment;
import com.example.weather.presentation.main.common.BaseMainPresenter;
import com.example.weather.presentation.main.common.ViewModelMapper;

import javax.inject.Inject;


@PerFragment
public class HomePresenter extends BaseMainPresenter<HomeView> {
    public static final String TAG = "tag_home_presenter";

    private CurrentWeatherInteractor currentWeatherInteractor;
    private ViewModelMapper viewMapper;

    @Inject
    public HomePresenter(CurrentWeatherInteractor currentWeatherInteractor,
                         ViewModelMapper viewMapper) {
        this.currentWeatherInteractor = currentWeatherInteractor;
        this.viewMapper = viewMapper;
    }

    public void getCurrentWeather(boolean force, boolean checkForCityChange) {
        if (getView() != null) {
            getView().showLoad();
        }
        getCompositeDisposable().add(
                currentWeatherInteractor.requestWeather(force, checkForCityChange).subscribe(
                        forecastModel -> {
                            if (getView() != null) {
                                getView().showWeather(viewMapper.forecastModelToViewModel(forecastModel));
                                getView().hideLoad();
                            }
                        }, throwable -> {
                            if (getView() != null) {
                                getView().showError(R.string.error);
                                getView().hideLoad();
                            }
                        }
                )
        );
    }
}
