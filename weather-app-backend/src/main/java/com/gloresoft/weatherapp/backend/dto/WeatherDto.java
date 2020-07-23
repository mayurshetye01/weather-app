package com.gloresoft.weatherapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@JsonIgnoreProperties
public class WeatherDto {
    @NotEmpty(message = "{field.required}")
    private String city;
    private String weatherDescription;
    private float temp;
    private float minTemp;
    private float maxTemp;
    private Date sunrise;
    private Date sunset;
    private String user;
}
