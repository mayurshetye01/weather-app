package com.gloresoft.weatherapp.backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApplicationParameters {
    @Value("${weather-api-host}")
    private String weatherApiHost;

    @Value("${weather-api-base-path}")
    private String weatherApiBasePath;

    @Value("${weather-api-key}")
    private String weatherApiKey;

    @Value("${weather-unit-key}")
    private String weatherUnitKey;

    @Value("${admin-username}")
    private String adminUserName;

    @Value("${admin-password}")
    private String adminPassword;
}
