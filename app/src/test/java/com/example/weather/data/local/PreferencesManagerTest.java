package com.example.weather.data.local;


import android.content.SharedPreferences;

import com.example.weather.data.local.PreferencesManager;

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
        float DEFAULT_LATITUDE = 55.7558F;
        assertEquals(DEFAULT_LATITUDE, preferencesManager.getLatitude());
    }

    @Test
    public void checkDefaultLongitude() {
        float DEFAULT_LONGITUDE = 37.6173F;
        assertEquals(DEFAULT_LONGITUDE, preferencesManager.getLongitude());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void checkDefaultFirstTimeUser() {
        boolean DEFAULT_FIRST_TIME_USER = true;
        assertEquals(DEFAULT_FIRST_TIME_USER, preferencesManager.getFirstTimeUser());
    }

    @Test
    public void setActualLatitude() {
        float ACTUAL_LATITUDE = 51.5074F;
        preferencesManager.setLatitude(ACTUAL_LATITUDE);

        assertEquals(ACTUAL_LATITUDE, preferencesManager.getLatitude());
    }

    @Test
    public void setActualLongitude() {
        float ACTUAL_LONGITUDE = 0.1278F;
        preferencesManager.setLongitude(ACTUAL_LONGITUDE);

        assertEquals(ACTUAL_LONGITUDE, preferencesManager.getLongitude());
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void setFirstTimeUser() {
        boolean ACTUAL_FIRST_TIME_USER = false;
        preferencesManager.setFirstTimeUser(ACTUAL_FIRST_TIME_USER);

        assertEquals(ACTUAL_FIRST_TIME_USER, preferencesManager.getFirstTimeUser());
    }
}
