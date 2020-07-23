package com.gloresoft.weatherapp.backend.services.impl;

import com.gloresoft.weatherapp.backend.config.ApplicationParameters;
import com.gloresoft.weatherapp.backend.converters.WeatherConverter;
import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.dto.openweather.response.CurrentWeatherResult;
import com.gloresoft.weatherapp.backend.dto.openweather.response.Weather;
import com.gloresoft.weatherapp.backend.exceptions.NoDataFoundException;
import com.gloresoft.weatherapp.backend.services.HistoryService;
import com.gloresoft.weatherapp.backend.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;

@Service
public class WeatherServiceImpl implements WeatherService {

    private static final String SCHEME = "http";
    private static final String CITY_NAME_QUERY_PARAM = "q";
    private static final String UNITS_QUERY_PARAM = "units";
    private static final String API_KEY_QUERY_PARAM = "appid";

    private RestTemplate restTemplate;
    private WeatherConverter weatherConverter;
    private ApplicationParameters applicationParameters;
    private HistoryService historyService;

    @Autowired
    public WeatherServiceImpl(RestTemplate restTemplate, ApplicationParameters applicationParameters,
                              WeatherConverter weatherConverter, HistoryService historyService) {
        this.restTemplate = restTemplate;
        this.applicationParameters = applicationParameters;
        this.weatherConverter = weatherConverter;
        this.historyService = historyService;
    }

    @Override
    public WeatherDto getCurrentWeather(String city) {
        MultiValueMap<String, String> queryMap = buildQueryMap(city);
        URI uri = buildURI(queryMap);

        CurrentWeatherResult currentWeatherResult = restTemplate.getForObject(uri, CurrentWeatherResult.class);
        if (currentWeatherResult == null || CollectionUtils.isEmpty(currentWeatherResult.getWeatherList()))
            throw new NoDataFoundException("No weather data found for city " + city);

        Weather weather = currentWeatherResult.getWeatherList().get(0);

        WeatherDto currentWeather = weatherConverter.convertToDto(weather);
        // The api does not return 'sunrise' and/or 'sunset' when queried by city. Hence adding random dates for sunrise time and sunset
        addRandomSunsriseAndSunset(currentWeather);
        historyService.addEntry(currentWeather);
        return currentWeather;
    }

    private URI buildURI(MultiValueMap<String, String> queryMap) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(applicationParameters.getWeatherApiHost())
                .path(applicationParameters.getWeatherApiBasePath())
                .queryParams(queryMap)
                .build()
                .toUri();
        return uri;
    }

    private MultiValueMap<String, String> buildQueryMap(String city) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(CITY_NAME_QUERY_PARAM, city);
        map.add(UNITS_QUERY_PARAM, applicationParameters.getWeatherUnitKey());
        map.add(API_KEY_QUERY_PARAM, applicationParameters.getWeatherApiKey());
        return map;
    }

    private void addRandomSunsriseAndSunset(WeatherDto weatherDto) {
        long currentTimeMillis = System.currentTimeMillis();
        weatherDto.setSunrise(new Date(currentTimeMillis));
        weatherDto.setSunset(new Date(currentTimeMillis + 100000000));
    }
}
