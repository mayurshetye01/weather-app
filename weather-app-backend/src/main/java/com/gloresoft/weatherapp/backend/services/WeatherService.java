package com.gloresoft.weatherapp.backend.services;

import com.gloresoft.weatherapp.backend.exceptions.NoDataFoundException;
import com.gloresoft.weatherapp.backend.dto.WeatherDto;

public interface WeatherService {

    WeatherDto getCurrentWeather(String city) throws NoDataFoundException;

}
