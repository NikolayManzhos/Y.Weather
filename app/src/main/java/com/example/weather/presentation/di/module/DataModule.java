package com.example.weather.presentation.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.weather.BuildConfig;
import com.example.weather.data.local.CacheManager;
import com.example.weather.data.local.PreferenceCacheManager;
import com.example.weather.data.repository.weather.WeatherRepositoryImpl;
import com.example.weather.data.WeatherApi;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.presentation.di.ApplicationContext;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    @Provides
    CacheManager provideCacheManager(@ApplicationContext Context context) {
        return new PreferenceCacheManager(context);
    }

    @Provides
    @Singleton
    SharedPreferences provideDefaultSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherProvider(WeatherApi weatherApi,
                                             CacheManager cacheManager,
                                             PreferencesManager preferencesManager) {
        return new WeatherRepositoryImpl(weatherApi, cacheManager, preferencesManager);
    }

    @Provides
    @Singleton
    WeatherApi provideWeatherApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(WeatherApi.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}
