package com.example.weather.utils;

import android.content.Context;
import android.view.View;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

/**
 * Created on 8/13/2017.
 */
@RunWith(RobolectricTestRunner.class)
public class KeyboardUtilsTest {

    @Test
    public void hideKeyboard() {
        KeyboardUtils keyboardUtils = new KeyboardUtils();
        Context context = RuntimeEnvironment.application.getApplicationContext();
        keyboardUtils.hideKeyboard(context, new View(context));
    }
}
