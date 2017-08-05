package com.example.weather.presentation.main.settings_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.weather.R;
import com.example.weather.presentation.android_job.WeatherJob;
import com.example.weather.presentation.common.BaseFragment;
import com.yarolegovich.mp.MaterialChoicePreference;
import com.yarolegovich.mp.MaterialSwitchPreference;

import butterknife.BindView;


public class SettingsFragment extends BaseFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @BindView(R.id.updateSwitchPreference)
    MaterialSwitchPreference switchPreference;

    @BindView(R.id.updateIntervalPreference)
    MaterialChoicePreference choicePreference;

    private final String TAG = "SettingsFragment";

    @Override
    protected int provideLayout() {
        return R.layout.fragment_settings;
    }

    @Nullable
    @Override
    protected String provideToolbarTitle() {
        return getString(R.string.settings_toolbar_title);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setChoicePreferenceStatus(switchPreference.getValue());

        switchPreference.setOnClickListener(v -> {
            if (switchPreference.getValue()) {
                long interval = Long.valueOf(choicePreference.getValue());
                WeatherJob.scheduleJob(interval);
            } else {
                WeatherJob.stopJob();
            }
            setChoicePreferenceStatus(switchPreference.getValue());
        });
    }

    private void setChoicePreferenceStatus(boolean updateSwitchValue) {
        if (updateSwitchValue) {
            choicePreference.setVisibility(View.GONE);
        } else {
            choicePreference.setVisibility(View.VISIBLE);
        }
    }
}
