package com.gloresoft.weatherapp.backend.rest.v1;

import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.services.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Slf4j
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/current")
    public WeatherDto getCurrentWeather(@RequestParam String city) {
        return weatherService.getCurrentWeather(city);
    }


}
