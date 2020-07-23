package com.gloresoft.weatherapp.backend.entities;

import com.gloresoft.weatherapp.backend.services.HistoryService;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "weather_history", uniqueConstraints = @UniqueConstraint(columnNames = {"city", "username"}))
@IdClass(HistoryId.class)
public class WeatherHistory {

    @Id
    @NotNull
    private String city;

    @Column(name = "weather_description")
    private String weatherDescription;

    private float temp;

    @Column(name = "temp_min")
    private float minTemp;

    @Column(name = "temp_max")
    private float maxTemp;
    
    private Date sunrise;
    private Date sunset;

    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

}
