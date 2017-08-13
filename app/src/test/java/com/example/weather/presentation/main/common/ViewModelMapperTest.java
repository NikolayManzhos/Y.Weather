package com.example.weather.presentation.main.common;

import com.example.weather.domain.models.ForecastModel;
import com.example.weather.presentation.main.home_screen.view_model.HomeViewModel;
import com.example.weather.utils.ConvertUtils;

import org.junit.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created on 8/13/2017.
 */

public class ViewModelMapperTest {

    @Test
    public void convertToViewModel() {
        ConvertUtils convertUtils = mock(ConvertUtils.class);
        ViewModelMapper mapper = new ViewModelMapper(convertUtils);
        ForecastModel forecastModel = provideRandomForecastModel();

        HomeViewModel viewModel = mapper.forecastModelToViewModel(forecastModel);

        assertNotNull(viewModel);
        assertEquals(forecastModel.getCityName(), viewModel.getCityName());
    }

    private ForecastModel provideRandomForecastModel() {
        return random(ForecastModel.class);
    }
}
