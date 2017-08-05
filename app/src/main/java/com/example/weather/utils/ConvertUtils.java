package com.example.weather.utils;

import com.example.weather.data.local.PreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.Math.*;

@Singleton
public class ConvertUtils {

    private PreferencesManager preferencesManager;

    @Inject
    public ConvertUtils(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    /**
     * Accept degrees in kelvin and converts to currently selected units.
     * Default temperature units is in celsius.
     * @param degrees Degrees from data source
     * @return temperature corresponding to settings (C/F)
     */
    public int convertTemperature(double degrees) {
        if (preferencesManager.getTemperatureUnits().equals("C")) {
            return (int) (degrees - 273.15);
        }
        return (int) (degrees * 1.8 - 459.67);
    }

    public int convertWindSpeed(double windSpeed) {
        switch (preferencesManager.getWindSpeedUnits()) {
            case "meters":
                return (int) round(windSpeed);
            case "kilometers":
                return  (int) round(3.6*windSpeed);
            case "miles":
                return (int) round(2.237*windSpeed);
            case "knots":
                return (int) round(1.9*windSpeed);
            default:
                return (int) round(windSpeed);
        }
    }
}
