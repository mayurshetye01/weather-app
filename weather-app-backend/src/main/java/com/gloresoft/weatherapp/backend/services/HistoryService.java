package com.gloresoft.weatherapp.backend.services;

import com.gloresoft.weatherapp.backend.dto.WeatherDto;

import java.util.List;

public interface HistoryService {
    List<WeatherDto> getAllRecords();

    List<WeatherDto> getUserSearchHistory();

    void addEntry(WeatherDto weather);

    void updateEntry(WeatherDto weather);

    void deleteEntry(String  city);

    void bulkDelete();
}
