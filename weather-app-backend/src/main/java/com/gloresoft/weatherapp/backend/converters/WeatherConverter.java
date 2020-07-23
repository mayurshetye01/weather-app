package com.gloresoft.weatherapp.backend.converters;

import com.gloresoft.weatherapp.backend.dto.WeatherDto;
import com.gloresoft.weatherapp.backend.dto.openweather.response.MainDetails;
import com.gloresoft.weatherapp.backend.dto.openweather.response.SysDetails;
import com.gloresoft.weatherapp.backend.dto.openweather.response.Weather;
import com.gloresoft.weatherapp.backend.dto.openweather.response.WeatherDetails;
import com.gloresoft.weatherapp.backend.entities.WeatherHistory;
import org.springframework.stereotype.Component;

@Component
public class WeatherConverter {

    // To Do -> Code duplicacy. Think of alternative
    public WeatherHistory convertToEntity(WeatherDto weatherDto) {
        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setCity(weatherDto.getCity());
        weatherHistory.setWeatherDescription(weatherDto.getWeatherDescription());
        weatherHistory.setMaxTemp(weatherDto.getMaxTemp());
        weatherHistory.setMinTemp(weatherDto.getMinTemp());
        weatherHistory.setSunrise(weatherDto.getSunrise());
        weatherHistory.setSunset(weatherDto.getSunset());
        weatherHistory.setTemp(weatherDto.getTemp());

        return weatherHistory;
    }

    public WeatherHistory convertToEntity(Weather weather) {
        if (weather == null)
            throw new RuntimeException("Weather entry is null");

        WeatherHistory weatherHistory = new WeatherHistory();
        weatherHistory.setCity(weather.getCity());

        MainDetails mainDetails = weather.getMainDetails();
        if (mainDetails != null) {
            weatherHistory.setTemp(weather.getMainDetails().getCurrentTemp());
            weatherHistory.setMaxTemp(weather.getMainDetails().getMaxTemp());
            weatherHistory.setMinTemp(weather.getMainDetails().getMinTemp());
        }

        SysDetails sysDetails = weather.getSysDetails();
        if (sysDetails != null) {
            weatherHistory.setSunrise(weather.getSysDetails().getSunrise());
            weatherHistory.setSunset(weather.getSysDetails().getSunset());
        }
        WeatherDetails[] details = weather.getWeatherDetails();

        // Check this
        if (details != null && details[0] != null)
            weatherHistory.setWeatherDescription(weather.getWeatherDetails()[0].getDescription());

        return weatherHistory;
    }

    public WeatherDto convertToDto(Weather weather) {
        if (weather == null)
            throw new RuntimeException("Weather entry is null");

        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setCity(weather.getCity());

        MainDetails mainDetails = weather.getMainDetails();
        if (mainDetails != null) {
            weatherDto.setTemp(weather.getMainDetails().getCurrentTemp());
            weatherDto.setMaxTemp(weather.getMainDetails().getMaxTemp());
            weatherDto.setMinTemp(weather.getMainDetails().getMinTemp());
        }

        SysDetails sysDetails = weather.getSysDetails();
        if (sysDetails != null) {
            weatherDto.setSunrise(weather.getSysDetails().getSunrise());
            weatherDto.setSunset(weather.getSysDetails().getSunset());
        }
        WeatherDetails[] details = weather.getWeatherDetails();

        // Check this
        if (details != null && details[0] != null)
            weatherDto.setWeatherDescription(weather.getWeatherDetails()[0].getDescription());

        return weatherDto;
    }

    public WeatherDto convertToDto(WeatherHistory weatherHistory) {
        WeatherDto weatherDto = new WeatherDto();
        weatherDto.setCity(weatherHistory.getCity());
        weatherDto.setMaxTemp(weatherHistory.getMaxTemp());
        weatherDto.setMinTemp(weatherHistory.getMinTemp());
        weatherDto.setSunrise(weatherHistory.getSunrise());
        weatherDto.setSunset(weatherHistory.getSunset());
        weatherDto.setTemp(weatherHistory.getTemp());
        weatherDto.setWeatherDescription(weatherHistory.getWeatherDescription());
        weatherDto.setUser(weatherHistory.getUser().getUsername());
        return weatherDto;
    }
}
