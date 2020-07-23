package com.gloresoft.weatherapp.backend.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties
public class CurrentWeatherResult {
    @JsonProperty("list")
    List<Weather> weatherList;
}
