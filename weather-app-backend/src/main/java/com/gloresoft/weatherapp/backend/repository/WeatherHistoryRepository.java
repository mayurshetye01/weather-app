package com.gloresoft.weatherapp.backend.repository;

import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.entities.WeatherHistory;

import java.util.List;

public interface WeatherHistoryRepository {

    WeatherHistory findByCity(String city, User user);

    List<WeatherHistory> getAllRecords();

    List<WeatherHistory> getAllRecordsForUser(User user);

    void saveOrUpdate(WeatherHistory weatherHistory);

    void update(WeatherHistory weatherHistory);

    void delete(WeatherHistory weatherHistory);

    void deleteAllForUser(List<WeatherHistory> weatherHistoryList);

}
