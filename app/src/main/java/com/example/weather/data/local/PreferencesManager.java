package com.example.weather.data.local;

import android.content.SharedPreferences;

public class PreferencesManager {

    private final String UPDATE_INTERVAL = "b_interval";
    private final String FIRST_TIME_USE = "b_first_time_user";
    private final String LAT = "lat";
    private final String LNG = "lng";

    private SharedPreferences sharedPreferences;

    public PreferencesManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getCurrentUpdateInterval() {
        return sharedPreferences.getString(UPDATE_INTERVAL, "3600000");
    }

    public boolean getFirstTimeUser() {
        return sharedPreferences.getBoolean(FIRST_TIME_USE, true);
    }

    public void setFirstTimeUser(boolean status) {
        sharedPreferences.edit().putBoolean(FIRST_TIME_USE, status).apply();
    }

    public void setLatitude(double latitude) {
        sharedPreferences.edit().putFloat(LAT, (float) latitude).apply();
    }

    public float getLatitude() {
        //Default Moscow
        return sharedPreferences.getFloat(LAT, (float) 55.7558);
    }

    public void setLongitude(double longitude) {
        sharedPreferences.edit().putFloat(LNG, (float) longitude).apply();
    }

    public float getLongitude() {
        //Default Moscow
        return sharedPreferences.getFloat(LNG, (float) 37.6173);
    }
}
