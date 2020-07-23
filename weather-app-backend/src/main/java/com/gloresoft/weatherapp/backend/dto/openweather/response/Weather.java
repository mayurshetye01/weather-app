package com.gloresoft.weatherapp.backend.dto.openweather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class Weather {

    @JsonProperty("name")
    private String city;

    @JsonProperty("weather")
    private WeatherDetails[] weatherDetails;

    @JsonProperty("main")
    private MainDetails mainDetails;

    @JsonProperty("sys")
    private SysDetails sysDetails;

}
