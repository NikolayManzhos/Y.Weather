package com.example.weather.presentation.main.detail_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.presentation.main.home_screen.view_model.WeatherViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import butterknife.BindView;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created on 8/13/2017.
 */

@RunWith(RobolectricTestRunner.class)
public class DetailFragmentTest {

    private DetailFragment detailFragment;

    @Before
    public void createFragmentWithArgs() {
        detailFragment = DetailFragment.newInstance(provideRandomViewModeL());
    }

    @Test
    public void verifyViewCalls() {
        detailFragment.icon = mock(ImageView.class);
        detailFragment.condition = mock(TextView.class);
        detailFragment.currentDate = mock(TextView.class);
        detailFragment.temperatureDay = mock(TextView.class);
        detailFragment.temperatureNight = mock(TextView.class);
        detailFragment.humidity = mock(TextView.class);
        detailFragment.pressure = mock(TextView.class);
        detailFragment.windSpeed = mock(TextView.class);

        detailFragment.onViewCreated(new View(RuntimeEnvironment.application.getApplicationContext()),new Bundle());

        verify(detailFragment.icon).setImageResource(anyInt());
        verify(detailFragment.condition).setText(anyString());
        verify(detailFragment.currentDate).setText(anyString());
        verify(detailFragment.temperatureDay).setText(anyString());
        verify(detailFragment.temperatureNight).setText(anyString());
        verify(detailFragment.humidity).setText(anyString());
        verify(detailFragment.pressure).setText(anyString());
        verify(detailFragment.windSpeed).setText(anyString());
    }

    private WeatherViewModel provideRandomViewModeL() {
        return random(WeatherViewModel.class);
    }
}
