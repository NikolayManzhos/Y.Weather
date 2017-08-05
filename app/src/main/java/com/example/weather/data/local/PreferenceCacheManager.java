package com.example.weather.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.weather.WeatherApp;
import com.example.weather.data.entities.weather.DetailedWeather;
import com.example.weather.presentation.di.ApplicationContext;
import com.google.gson.Gson;

public class PreferenceCacheManager implements CacheManager {

    public static final String TAG = "tag_preference_cache";
    private Context context;
    private final String KEY = "last_weather_pref_key";

    public PreferenceCacheManager(@ApplicationContext Context context) {
        this.context = context;
    }

    @Override
    public void saveWeather(DetailedWeather weather) {
        Gson gson = new Gson();
        String json = gson.toJson(weather);
        SharedPreferences prefs = context.getSharedPreferences(WeatherApp.PREF_NAME,
                Context.MODE_PRIVATE);
        prefs.edit().putString(KEY, json).apply();
    }

    @Override
    public DetailedWeather getLastWeather() {
        SharedPreferences prefs = context.getSharedPreferences(WeatherApp.PREF_NAME,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY, "");
        return gson.fromJson(json, DetailedWeather.class);
    }
}
