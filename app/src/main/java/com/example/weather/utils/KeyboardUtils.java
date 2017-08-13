package com.example.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created on 8/13/2017.
 */
@Singleton
public class KeyboardUtils {

    @Inject
    KeyboardUtils() {}

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
