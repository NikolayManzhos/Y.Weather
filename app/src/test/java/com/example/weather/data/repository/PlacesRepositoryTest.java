package com.example.weather.data.repository;


import com.example.weather.data.entities.autocomplete.SuggestResponse;
import com.example.weather.data.entities.details.DetailsResponse;
import com.example.weather.data.entities.details.Location;
import com.example.weather.data.entities.details.Result;
import com.example.weather.data.local.PreferencesManager;
import com.example.weather.data.local.RealmHelper;
import com.example.weather.data.network.PlacesApi;
import com.example.weather.data.repository.suggest.PlacesRepository;
import com.example.weather.data.repository.suggest.PlacesRepositoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.UnknownHostException;

import io.reactivex.Single;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlacesRepositoryTest {

    @Mock
    private PlacesApi placesApi;

    @Mock
    private PreferencesManager preferenceManager;

    @Mock
    private RealmHelper realmHelper;

    private PlacesRepository repository;
    private final String PLACE_ID = "Kn1oi90aJAMWLBN";


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        repository = new PlacesRepositoryImpl(placesApi, preferenceManager, realmHelper);
    }

    @Test
    public void getSuggestionsCall() {
        SuggestResponse suggestResponse = provideRandomResponse();
        when(placesApi.getSuggestions(anyString(), anyString(), anyString())).thenReturn(Single.just(suggestResponse));

        String QUERY = "Mos";
        repository.getSuggestions(QUERY)
                .test()
                .assertNoErrors()
                .assertValueCount(1)
                .assertValue(suggestResponse);
    }

    @Test
    public void getPlaceDetailsCallSuccess() {
        DetailsResponse detailsResponse = provideRandomDetailsResponse();
        when(placesApi.getPlaceDetails(anyString(), anyString(), anyString())).thenReturn(Single.just(detailsResponse));
        when(realmHelper.checkCurrentPlaceInFavorites(anyDouble(), anyDouble())).thenReturn(Single.just(false));

        repository.getPlaceDetails(PLACE_ID)
                .test()
                .assertNoErrors()
                .assertComplete();

        verify(realmHelper).removeForecast(anyDouble(), anyDouble());


        Result result = detailsResponse.getResult();
        Location location = result.getGeometry().getLocation();
        verify(preferenceManager).setCurrentCityName(result.getVicinity());
        verify(preferenceManager).setCurrentLatitude(location.getLat());
        verify(preferenceManager).setCurrentLongitude(location.getLng());
    }

    @Test
    public void getPlaceDetailsFailure() {
        Exception networkException = new UnknownHostException("Connection failed.");
        when(placesApi.getPlaceDetails(anyString(), anyString(), anyString())).thenReturn(Single.error(networkException));

        repository.getPlaceDetails(PLACE_ID)
                .test()
                .assertNoValues()
                .assertError(networkException);

        verify(preferenceManager, never()).setCurrentCityName(anyString());
        verify(preferenceManager, never()).setCurrentLatitude(anyDouble());
        verify(preferenceManager, never()).setCurrentLongitude(anyDouble());
    }

    private SuggestResponse provideRandomResponse() {
        return random(SuggestResponse.class);
    }

    private DetailsResponse provideRandomDetailsResponse() {
        return random(DetailsResponse.class);
    }
}
