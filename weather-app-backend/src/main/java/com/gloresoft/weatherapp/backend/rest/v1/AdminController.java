package com.gloresoft.weatherapp.backend.rest.v1;

import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.services.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    HistoryService historyService;

    @GetMapping("/history")
    public Map<String, List<WeatherDto>> getUserHistoryMap() {
        List<WeatherDto> allRecords = historyService.getAllRecords();
        Map<String, List<WeatherDto>> userHistoryMap = allRecords.stream().collect(
                Collectors.groupingBy(WeatherDto::getUser, HashMap::new, Collectors.toList()
                )
        );
        return userHistoryMap;
    }
}
