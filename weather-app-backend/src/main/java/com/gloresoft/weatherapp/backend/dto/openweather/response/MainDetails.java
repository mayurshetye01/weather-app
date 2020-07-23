package com.gloresoft.weatherapp.backend.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MainDetails {
    @JsonProperty("temp")
    private float currentTemp;

    @JsonProperty("temp_min")
    private float minTemp;

    @JsonProperty("temp_max")
    private float maxTemp;

}
