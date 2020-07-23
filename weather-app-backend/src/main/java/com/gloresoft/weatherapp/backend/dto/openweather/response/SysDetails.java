package com.gloresoft.weatherapp.backend.dto.openweather.response;

import lombok.Data;

import java.util.Date;

@Data
public class SysDetails {
    private Date sunrise;
    private Date sunset;
}
