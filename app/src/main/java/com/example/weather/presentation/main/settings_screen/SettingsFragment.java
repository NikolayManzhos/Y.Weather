package com.example.weather.presentation.main.settings_screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.weather.R;
import com.example.weather.presentation.android_job.WeatherJob;
import com.example.weather.presentation.common.BaseFragment;
import com.example.weather.utils.OnCityChangeListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.yarolegovich.mp.MaterialChoicePreference;
import com.yarolegovich.mp.MaterialStandardPreference;
import com.yarolegovich.mp.MaterialSwitchPreference;

import butterknife.BindView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class SettingsFragment extends BaseFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @BindView(R.id.updateSwitchPreference)
    MaterialSwitchPreference switchPreference;

    @BindView(R.id.updateIntervalPreference)
    MaterialChoicePreference choicePreference;

    @BindView(R.id.settingsCity)
    MaterialStandardPreference city;

    private OnCityChangeListener cityChangeListener;

    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private final String TAG = "SettingsFragment";

    @Override
    protected int provideLayout() {
        return R.layout.fragment_settings;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCityChangeListener) {
            cityChangeListener = (OnCityChangeListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cityChangeListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
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

        city.setOnClickListener(v -> {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .setFilter(typeFilter)
                        .build(getActivity());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                Log.d(TAG, e.getMessage());
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.d(TAG, e.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                cityChangeListener.cityChanged(place.getLatLng());
                Log.d(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.d(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "Place request canceled");
            }
        }
    }

    private void setChoicePreferenceStatus(boolean updateSwitchValue) {
        if (updateSwitchValue) {
            choicePreference.setVisibility(View.GONE);
        } else {
            choicePreference.setVisibility(View.VISIBLE);
        }
    }
}
