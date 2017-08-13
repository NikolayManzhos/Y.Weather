package com.example.weather;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.weather.presentation.main.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class SettingsScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_settings));
    }

    @Test
    public void settingsShouldBeDisplayed() {
        onView(withId(R.id.updateSwitchPreference)).check(matches(isDisplayed()));
        onView(withId(R.id.updateIntervalPreference)).check(matches(not(isDisplayed())));
        onView(withId(R.id.unitsTemperature)).check(matches(isDisplayed()));
        onView(withId(R.id.unitsWindSpeed)).check(matches(isDisplayed()));
        onView(withId(R.id.unitsPressure)).check(matches(isDisplayed()));
    }

    @Test
    public void temperatureSettingsShouldBeSelected() {
        onView(withId(R.id.unitsTemperature)).perform(click());
    }

    @Test
    public void windSpeedSettingShouldBeSelected() {
        onView(withId(R.id.unitsWindSpeed)).perform(click());
    }

    @Test
    public void pressureSettingShouldBeSelected() {
        onView(withId(R.id.unitsPressure)).perform(click());
    }

}
