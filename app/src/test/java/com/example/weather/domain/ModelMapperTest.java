package com.example.weather.domain;

import com.example.weather.data.entities.weather.ForecastWeather;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.domain.models.ForecastModel;
import com.example.weather.utils.ConvertUtils;

import org.junit.Test;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created on 8/13/2017.
 */

public class ModelMapperTest {

    @Test
    public void mapEntityToModel() {
        PreferencesManager preferencesManager = mock(PreferencesManager.class);
        ConvertUtils convertUtils = mock(ConvertUtils.class);
        ModelMapper mapper = new ModelMapper(preferencesManager, convertUtils);
        ForecastWeather response = provideRandomForecast();

        ForecastModel result = mapper.entityToModel(response);

        assertEquals(response.getList().get(0).getHumidity(), result.getCurrentWeatherModels().get(0).getHumidity());
        assertNotNull(result.getCurrentWeatherModels());

    }

    private ForecastWeather provideRandomForecast() {
        return random(ForecastWeather.class);
    }
}