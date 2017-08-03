package com.example.weather.presentation.android_job;

import android.support.annotation.NonNull;
import android.util.Log;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobManager;
import com.evernote.android.job.JobRequest;
import com.example.weather.WeatherApp;
import com.example.weather.domain.interactor.CurrentWeatherInteractor;

import javax.inject.Inject;

public class WeatherJob extends Job {

    static final String TAG = "job_weather_tag";

    @Inject
    CurrentWeatherInteractor currentWeatherInteractor;

    private Result result;

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        inject();
        Log.i(TAG, "onRunJob: ");
        currentWeatherInteractor.requestWeather(true).subscribe(detailedWeather -> {
            Log.i(TAG, "onRunJob: get weather");
            result = Result.SUCCESS;
        }, throwable -> {
            Log.i(TAG, "onRunJob: error" + throwable.getMessage());
            result = Result.RESCHEDULE;
        });
        return result;
    }

    public static void scheduleJob(long interval) {
        Log.i(TAG, "scheduleJob: ");

        new JobRequest.Builder(WeatherJob.TAG)
                .setPersisted(true)
                .setUpdateCurrent(true)
                .setPeriodic(interval)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .build()
                .schedule();
    }

    public static void stopJob() {
        Log.d(TAG, "stopJob");

        JobManager.instance().cancelAllForTag(TAG);
    }

    private void inject() {
        ((WeatherApp) getContext()).getAppComponent().inject(this);
    }
}
