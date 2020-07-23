package com.gloresoft.weatherapp.backend.entities;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class HistoryId implements Serializable {
    private String city;
    private User user;

    public HistoryId() {
    }

    public HistoryId(String city, User user) {
        this.city = city;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryId that = (HistoryId) o;

        if (!user.getUsername().equals(that.user.getUsername())) return false;
        return city.equals(that.city);
    }

    @Override
    public int hashCode() {
        int result = user.getUsername().hashCode();
        result = 31 * result + city.hashCode();
        return result;
    }
}