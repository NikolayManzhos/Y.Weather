package com.example.weather.data.local;


import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class PreferencesManagerTest {

    private SharedPreferences sharedPreferences;
    private PreferencesManager preferencesManager;


    @Before
    public void setup() {
        String SP_NAME = "TEST_SP";
        sharedPreferences = RuntimeEnvironment.
                application.getSharedPreferences(SP_NAME, 0);
        sharedPreferences.edit().clear().apply();
        preferencesManager = new PreferencesManager(sharedPreferences);
    }

    @Test
    public void checkPreferencesInitialization() {
        assertNotNull(sharedPreferences);
        assertNotNull(preferencesManager);
    }

    @Test
    public void checkDefaultUpdateInterval() {
        String DEFAULT_UPDATE_INTERVAL = "3600000";
        assertEquals(DEFAULT_UPDATE_INTERVAL, preferencesManager.getCurrentUpdateInterval());
    }

    @Test
    public void checkDefaultLatitude() {
        double DEFAULT_LATITUDE = 55.7558;
        assertEquals(DEFAULT_LATITUDE, preferencesManager.getCurrentLatitude());
    }

    @Test
    public void checkDefaultLongitude() {
        double DEFAULT_LONGITUDE = 37.6173;
        assertEquals(DEFAULT_LONGITUDE, preferencesManager.getCurrentLongitude());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void checkDefaultFirstTimeUser() {
        boolean DEFAULT_FIRST_TIME_USER = true;
        assertEquals(DEFAULT_FIRST_TIME_USER, preferencesManager.getFirstTimeUser());
    }

    @Test
    public void setActualLatitude() {
        double ACTUAL_LATITUDE = 51.5074;
        preferencesManager.setCurrentLatitude(ACTUAL_LATITUDE);

        assertEquals(ACTUAL_LATITUDE, preferencesManager.getCurrentLatitude());
    }

    @Test
    public void setActualLongitude() {
        double ACTUAL_LONGITUDE = 0.1278;
        preferencesManager.setCurrentLongitude(ACTUAL_LONGITUDE);

        assertEquals(ACTUAL_LONGITUDE, preferencesManager.getCurrentLongitude());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void setFirstTimeUser() {
        boolean ACTUAL_FIRST_TIME_USER = false;
        preferencesManager.setFirstTimeUser(ACTUAL_FIRST_TIME_USER);

        assertEquals(ACTUAL_FIRST_TIME_USER, preferencesManager.getFirstTimeUser());
    }

    @Test
    public void checkDefaultTempUnits() {
        String DEFAULT_TEMP_UNITS = "C";
        assertEquals(DEFAULT_TEMP_UNITS, preferencesManager.getTemperatureUnits());
    }

    @Test
    public void checkDefaultWindSpeedUnits() {
        String DEFAULT_WIND_SPEED_UNITS = "meters";
        assertEquals(DEFAULT_WIND_SPEED_UNITS, preferencesManager.getWindSpeedUnits());
    }

    @Test
    public void checkDefaultCityName() {
        String DEFAULT_CITY_NAME = "Moscow";
        assertEquals(DEFAULT_CITY_NAME, preferencesManager.getCurrentCityName());
    }

    @Test
    public void setCurrentCityName() {
        String CITY_NAME = "Sidney";
        preferencesManager.setCurrentCityName(CITY_NAME);

        assertEquals(CITY_NAME, preferencesManager.getCurrentCityName());
    }

    @Test
    public void checkPressureDefault() {
        String DEFAULT_PRESSURE = "hpa";
        assertEquals(DEFAULT_PRESSURE, preferencesManager.getPressureUnits());
    }
}
