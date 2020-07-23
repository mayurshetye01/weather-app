package com.gloresoft.weatherapp.backend.services;

import com.gloresoft.weatherapp.backend.config.ApplicationParameters;
import com.gloresoft.weatherapp.backend.converters.WeatherConverter;
import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.dto.openweather.response.CurrentWeatherResult;
import com.gloresoft.weatherapp.backend.dto.openweather.response.Weather;
import com.gloresoft.weatherapp.backend.exceptions.NoDataFoundException;
import com.gloresoft.weatherapp.backend.services.impl.WeatherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @InjectMocks
    WeatherServiceImpl weatherService;

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private WeatherConverter weatherConverter;

    @Mock
    private ApplicationParameters applicationParameters;

    @Mock
    private HistoryService historyService;

    @Test
    public void getCurrentWeatherTest() {
        String testCity = "test";
        Weather dummyWeather = new Weather();
        dummyWeather.setCity(testCity);
        CurrentWeatherResult dummyWeatherResult = new CurrentWeatherResult();
        dummyWeatherResult.setWeatherList(Arrays.asList(dummyWeather));

        when(restTemplate.getForObject(any(URI.class), eq(CurrentWeatherResult.class))).thenReturn(dummyWeatherResult);

        WeatherDto result = weatherService.getCurrentWeather(testCity);

        assertEquals(result.getCity(), testCity);

        // Verify that one entry is made in history
        verify(historyService, times(1)).addEntry(result);

    }

    @Test
    public void getCurrentWeatherEmptyDataTest() {
        String testCity = "test";

        when(restTemplate.getForObject(any(URI.class), eq(CurrentWeatherResult.class))).thenReturn(new ResponseEntity<CurrentWeatherResult>(HttpStatus.NO_CONTENT).getBody());

        assertThrows(NoDataFoundException.class, () -> weatherService.getCurrentWeather(testCity));

        verifyNoInteractions(historyService);

    }

}
