package com.gloresoft.weatherapp.backend.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class WeatherDetails {
    private String id;
    private String main;
    private String description;
}
