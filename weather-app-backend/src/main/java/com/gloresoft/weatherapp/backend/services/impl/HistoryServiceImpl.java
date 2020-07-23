package com.gloresoft.weatherapp.backend.services.impl;

import com.gloresoft.weatherapp.backend.converters.WeatherConverter;
import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.entities.WeatherHistory;
import com.gloresoft.weatherapp.backend.repository.UserRepository;
import com.gloresoft.weatherapp.backend.repository.WeatherHistoryRepository;
import com.gloresoft.weatherapp.backend.services.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {

    private WeatherHistoryRepository weatherHistoryRepository;
    private UserRepository userRepository;

    private WeatherConverter weatherConverter;

    @Autowired
    public HistoryServiceImpl(WeatherHistoryRepository weatherHistoryRepository,
                              UserRepository userRepository,
                              WeatherConverter weatherConverter) {
        this.weatherHistoryRepository = weatherHistoryRepository;
        this.weatherConverter = weatherConverter;
        this.userRepository = userRepository;
    }

    @Override
    public List<WeatherDto> getAllRecords() {
        List<WeatherHistory> weatherHistoryList = this.weatherHistoryRepository.getAllRecords();
        List<WeatherDto> allRecords = new ArrayList<>();
        weatherHistoryList.forEach(entry ->
                allRecords.add(weatherConverter.convertToDto(entry)));
        return allRecords;
    }

    @Override
    public List<WeatherDto> getUserSearchHistory() {
        List<WeatherHistory> weatherHistoryList = weatherHistoryRepository.getAllRecordsForUser(getLoggedInUser());
        List<WeatherDto> userSearchHistory = new ArrayList<>();
        weatherHistoryList.forEach(entry ->
                userSearchHistory.add(weatherConverter.convertToDto(entry)));
        return userSearchHistory;
    }

    @Override
    public void addEntry(WeatherDto weatherDto) {
        WeatherHistory weatherHistory = weatherConverter.convertToEntity(weatherDto);
        weatherHistory.setUser(getLoggedInUser());

        weatherHistoryRepository.saveOrUpdate(weatherHistory);
    }

    @Override
    public void updateEntry(WeatherDto weatherDto) {
        WeatherHistory weatherHistory = weatherConverter.convertToEntity(weatherDto);
        weatherHistory.setUser(getLoggedInUser());

        weatherHistoryRepository.update(weatherHistory);
    }

    @Override
    public void deleteEntry(String city) {
        WeatherHistory entity = weatherHistoryRepository.findByCity(city, getLoggedInUser());
        weatherHistoryRepository.delete(entity);
    }

    @Override
    public void bulkDelete() {
        List<WeatherHistory> weatherHistoryList = weatherHistoryRepository.getAllRecordsForUser(getLoggedInUser());
        weatherHistoryRepository.deleteAllForUser(weatherHistoryList);
    }

    private User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();
        return userRepository.findUserByUsername(username);
    }
}
