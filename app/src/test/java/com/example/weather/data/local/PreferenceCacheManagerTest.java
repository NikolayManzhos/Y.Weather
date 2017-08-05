package com.example.weather.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.weather.WeatherApp;
import com.example.weather.data.entities.weather.DetailedWeather;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PreferenceCacheManagerTest {

    private PreferenceCacheManager preferenceCacheManager;

    @Before
    public void setup() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        preferenceCacheManager = new PreferenceCacheManager(context);
        SharedPreferences sp = context.getSharedPreferences(WeatherApp.PREF_NAME, 0);
        sp.edit().clear().apply();
    }

    @Test
    public void checkSpInitialized() {
        assertNotNull(preferenceCacheManager);
    }

    @Test
    public void checkNoWeatherInCache() {
        assertNull(preferenceCacheManager.getLastWeather());
    }

    @Test
    public void saveResponseToCache() {
        String cityName = "Moscow";
        DetailedWeather response = new DetailedWeather();
        response.setName(cityName);
        preferenceCacheManager.saveWeather(response);

        assertNotNull(preferenceCacheManager.getLastWeather());
        assertEquals(cityName, response.getName());
    }
}
