package com.example.weather;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.NonNull;


public class TestResources extends Resources {

    public TestResources() {
        super(null, null, null);
    }

    @NonNull
    @Override
    public String getString(int id) throws NotFoundException {
        switch (id) {
            case R.string.meters:
                return "M/S";
            case R.string.kilometers:
                return "Km/H";
            case R.string.miles:
                return "Mph";
            case R.string.knots:
                return "Knots";
            case R.string.hPa:
                return "hPa";
            case R.string.mbar:
                return "mBar";
        }

        throw new NotFoundException();
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String getString(int id, Object... formatArgs) throws NotFoundException {
        switch (id) {
            case R.string.degree:
                return String.format("%d°", (int) formatArgs[0]);
            case R.string.percent:
                return String.format("%d%%", (int) formatArgs[0]);
        }

        throw new NotFoundException();
    }
}
