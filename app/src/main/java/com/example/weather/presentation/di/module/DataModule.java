package com.example.weather.presentation.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.weather.BuildConfig;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.network.PlacesApi;
import com.example.weather.data.network.WeatherApi;
import com.example.weather.data.repository.main.MainViewRepository;
import com.example.weather.data.repository.main.MainViewRepositoryImpl;
import com.example.weather.data.repository.suggest.PlacesRepository;
import com.example.weather.data.repository.suggest.PlacesRepositoryImpl;
import com.example.weather.data.repository.weather.WeatherRepository;
import com.example.weather.data.repository.weather.WeatherRepositoryImpl;
import com.example.weather.domain.ModelMapper;
import com.example.weather.presentation.di.ApplicationContext;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
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
    @Singleton
    SharedPreferences provideDefaultSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    WeatherRepository provideWeatherProvider(@Named("weather") WeatherApi weatherApi,
                                             PreferencesManager preferencesManager,
                                             RealmHelper realmHelper,
                                             ModelMapper mapper) {
        return new WeatherRepositoryImpl(weatherApi, preferencesManager, realmHelper, mapper);
    }

    @Provides
    @Singleton
    PlacesRepository provideSuggestRepository(@Named("places") PlacesApi placesApi,
                                              PreferencesManager preferencesManager) {
        return new PlacesRepositoryImpl(placesApi, preferencesManager);
    }

    @Provides
    @Singleton
    MainViewRepository provideMainViewRepository(RealmHelper realmHelper,
                                                 PreferencesManager preferencesManager) {
        return new MainViewRepositoryImpl(realmHelper, preferencesManager);
    }

    @Provides
    @Singleton
    @Named("weather")
    WeatherApi provideWeatherApi(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHER_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(WeatherApi.class);
    }

    @Provides
    @Singleton
    @Named("places")
    PlacesApi providePlacesApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.PLACES_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(PlacesApi.class);
    }

    @Provides
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
