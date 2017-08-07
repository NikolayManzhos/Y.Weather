package com.example.weather.utils;

import android.content.Context;
import android.content.res.Resources;

import com.example.weather.R;
import com.example.weather.data.local.PreferencesManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import static java.lang.Math.*;

@Singleton
public class ConvertUtils {

    private PreferencesManager preferencesManager;
    private Resources resources;

    @Inject
    ConvertUtils(PreferencesManager preferencesManager,
                 Resources resources) {
        this.preferencesManager = preferencesManager;
        this.resources = resources;
    }

    /**
     * Accept degrees in kelvin and converts to currently selected units.
     * Default temperature units is in celsius.
     * @param degrees Degrees from data source
     * @return temperature corresponding to settings (C/F)
     */
    public String convertTemperature(double degrees) {
        int temp;
        if (preferencesManager.getTemperatureUnits().equals("C")) {
            temp = (int) (degrees - 273.15);
            return resources.getString(R.string.degree, temp);
        }
        temp = (int) (degrees * 1.8 - 459.67);
        return resources.getString(R.string.degree, temp);
    }

    public String convertWindSpeed(double windSpeed) {
        int speed;
        String result;
        switch (preferencesManager.getWindSpeedUnits()) {
            case "meters":
                speed = (int) round(windSpeed);
                result = String.valueOf(speed) + resources.getString(R.string.meters);
                break;
            case "kilometers":
                speed = (int) round(3.6*windSpeed);
                result = String.valueOf(speed) + resources.getString(R.string.kilometers);
                break;
            case "miles":
                speed = (int) round(2.237*windSpeed);
                result = String.valueOf(speed) + resources.getString(R.string.miles);
                break;
            case "knots":
                speed = (int) round(1.9*windSpeed);
                result = String.valueOf(speed) + resources.getString(R.string.knots);
                break;
            default:
                speed = (int) round(windSpeed);
                result = String.valueOf(speed) + resources.getString(R.string.meters);
                break;
        }
        return result;
    }

    public String convertPressure(double pressure) {
        String result;
        int pressureInt = (int) round(pressure);
        switch (preferencesManager.getPressureUnits()) {
            case "hpa":
                result = String.valueOf(pressureInt) + resources.getString(R.string.hPa);
                break;
            case "mbar":
                result = String.valueOf(pressureInt) + resources.getString(R.string.mbar);
                break;
            default:
                result = String.valueOf(pressureInt) + resources.getString(R.string.hPa);
                break;
        }
        return result;
    }

    public String convertHumidity(int humidity) {
        return resources.getString(R.string.percent, humidity);
    }

    public int convertId(int weatherId) {
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_wind;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_wind;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear_day;
        } else if (weatherId == 801) {
            return R.drawable.ic_partly_cloudy_day;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return R.drawable.ic_clear_day;
    }
}
