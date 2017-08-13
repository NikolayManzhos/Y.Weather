package com.example.weather.presentation.android_job;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.weather.WeatherApp;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.repository.weather.WeatherRepository;

import javax.inject.Inject;

public class WeatherJob extends Job {

    static final String TAG = "job_weather_tag";

    @Inject
    WeatherRepository weatherRepository;

    @Inject
    PreferencesManager preferencesManager;

    @Inject
    RealmHelper realmHelper;

    private Result result;

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        inject();
        Log.i(TAG, "onRunJob: ");
        weatherRepository.getWeather(true).subscribe(detailedWeather -> {
            Log.i(TAG, "onRunJob: get weather");
            double latitude = preferencesManager.getCurrentLatitude();
            double longitude = preferencesManager.getCurrentLongitude();
            if (detailedWeather.getLatitude() != latitude || detailedWeather.getLongitude() != longitude) {
                realmHelper.removeForecast(detailedWeather.getLatitude(), detailedWeather.getLongitude());
            }
            result = Result.SUCCESS;
        }, throwable -> {
            Log.i(TAG, "onRunJob: error" + throwable.getMessage());
            result = Result.FAILURE;
        });
        return result;
    }

    public static void scheduleJob(long interval) {
        Log.i(TAG, "scheduleJob: ");

        new JobRequest.Builder(WeatherJob.TAG)
                .setPersisted(true)
                .setPeriodic(interval)
                .setRequiresDeviceIdle(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }

    public static void stopJob() {
        Log.d(TAG, "stopJob");

        JobManager.instance().cancelAllForTag(TAG);
    }

    private void inject() {
        ((WeatherApp) getContext().getApplicationContext()).getAppComponent().inject(this);
    }
}
