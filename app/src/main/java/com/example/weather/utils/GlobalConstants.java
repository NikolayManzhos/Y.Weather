package com.example.weather.utils;


public class GlobalConstants {

    public static final String EXTRAS_DETAILED_WEATHER = "detailed_weather";

    public static final String EVENT_FAVORITES_CHANGED = "event_fav_changed";
    public static final String EVENT_FAVORITE_ADDED_REMOVED = "event_fav_added_removed";

    private GlobalConstants() {
        throw new AssertionError("No instances please");
    }
}
